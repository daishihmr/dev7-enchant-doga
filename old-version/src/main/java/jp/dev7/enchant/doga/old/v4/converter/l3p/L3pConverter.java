package jp.dev7.enchant.doga.old.v4.converter.l3p;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import jp.dev7.enchant.doga.old.v4.converter.EnchantMesh;
import jp.dev7.enchant.doga.old.v4.converter.suf.SufConverter;
import jp.dev7.enchant.doga.old.v4.parser.atr.AtrFileParser;
import jp.dev7.enchant.doga.old.v4.parser.atr.ParseException;
import jp.dev7.enchant.doga.old.v4.parser.atr.data.Atr;
import jp.dev7.enchant.doga.old.v4.parser.atr.data.Color;
import jp.dev7.enchant.doga.old.v4.parser.l3p.L3pFileParser;
import jp.dev7.enchant.doga.old.v4.parser.l3p.data.L3p;
import jp.dev7.enchant.doga.old.v4.parser.l3p.data.Part;
import jp.dev7.enchant.doga.old.v4.parser.suf.SufFileParser;
import jp.dev7.enchant.doga.old.v4.parser.suf.data.Obj;
import jp.dev7.enchant.doga.old.v4.parser.suf.data.Prim;
import jp.dev7.enchant.doga.old.v4.parser.suf.data.Suf;
import jp.dev7.enchant.doga.old.v4.parser.suf.data.Vertex;
import jp.dev7.enchant.doga.old.v4.util.Utils;
import net.arnx.jsonic.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class L3pConverter {

	private final Logger logger = LoggerFactory.getLogger(L3pConverter.class);
	private final SufConverter sufConverter;

	public L3pConverter() throws IOException, ParseException {
		sufConverter = new SufConverter();
		sufConverter.loadGenieAtr();
	}

	public String convertToJson(File l3pFile) throws Exception {
		final StringWriter result = new StringWriter();
		convertAndWriteJson(l3pFile, result);
		return result.toString();
	}

	public String convertToJson(File l3pFile, Matrix4d transform)
			throws Exception {
		final StringWriter result = new StringWriter();
		convertAndWriteJson(l3pFile, transform, result);
		return result.toString();
	}

	public void convertAndWriteJson(File l3pFile, OutputStream out)
			throws Exception {
		final List<EnchantMesh> result = convert(l3pFile);
		JSON.encode(result, out, false);
	}

	public void convertAndWriteJson(File l3pFile, Matrix4d transform,
			OutputStream out) throws Exception {
		final List<EnchantMesh> result = convert(l3pFile, transform);
		JSON.encode(result, out, false);
	}

	public void convertAndWriteJson(File l3pFile, Appendable appendable)
			throws Exception {
		final List<EnchantMesh> result = convert(l3pFile);
		JSON.encode(result, appendable, false);
	}

	public void convertAndWriteJson(File l3pFile, Matrix4d transform,
			Appendable appendable) throws Exception {
		final List<EnchantMesh> result = convert(l3pFile, transform);
		JSON.encode(result, appendable, false);
	}

	public List<EnchantMesh> convert(File l3pFile) throws Exception {
		final Matrix4d transform = new Matrix4d();
		transform.setIdentity();
		return convert(l3pFile, transform);
	}

	public List<EnchantMesh> convert(final File l3pFile,
			final Matrix4d transform) throws Exception {
		final L3pFileParser l3pFileParser = new L3pFileParser();
		final L3p l3p = l3pFileParser.parse(l3pFile);

		sufConverter.putAllAtr(l3p.getPalette());
		logger.debug("atrMap = " + sufConverter.getAtrMap());

		final Suf dest = new Suf();
		int i = 0;
		for (final Part part : l3p.getObjects()) {
			final List<Obj> destObjects = Lists.newArrayList();

			logger.debug("パーツ" + (++i) + " : " + part.getName());
			File sufFile = Utils.dogaPartsFile(part.getSufFileName(), l3pFile);
			if (sufFile == null) {
				continue;
			}

			final Suf orig = SufFileParser.parse(sufFile);
			logger.debug("SUFファイル " + sufFile + "をロード");
			if (logger.isDebugEnabled()) {
				int cnt = 0;
				for (Obj obj : orig.getObjects()) {
					for (Prim prim : obj.getPrimitives()) {
						cnt += prim.getVertices().size();
					}
				}
				logger.debug("頂点数 = " + cnt);
			}
			File atrFile = new File(sufFile.getAbsolutePath().replace(".suf",
					".atr"));
			if (atrFile.exists()) {
				List<Atr> atrs = AtrFileParser.parse(atrFile);
				sufConverter.putAllAtr(atrs);
				logger.debug("ATRファイル " + atrFile + "をロード");
			}

			int j = 0;
			for (Obj origObj : orig.getObjects()) {
				logger.debug("    obj(" + j + ")を変換開始");
				if (logger.isDebugEnabled()) {
					int cnt = 0;
					for (Prim p : origObj.getPrimitives()) {
						cnt += p.getVertices().size();
					}
					logger.debug("    頂点数 = " + cnt);
				}

				Obj destObj = transform(origObj, part);

				logger.debug("    obj(" + j + ")を変換終了");
				if (logger.isDebugEnabled()) {
					int cnt = 0;
					for (Prim p : destObj.getPrimitives()) {
						cnt += p.getVertices().size();
					}
					logger.debug("    頂点数 = " + cnt);
					j++;
				}

				destObjects.add(destObj);
			}

			logger.debug("SUFの変換に成功");
			if (logger.isDebugEnabled()) {
				int cnt = 0;
				for (Obj obj : destObjects) {
					for (Prim prim : obj.getPrimitives()) {
						cnt += prim.getVertices().size();
					}
				}
				logger.debug("頂点数 = " + cnt);
			}

			dest.getObjects().addAll(destObjects);
		}

		final List<EnchantMesh> meshList = sufConverter.convert(dest);
		if (logger.isDebugEnabled()) {
			logger.debug("テクスチャ座標デバッグ");
			for (EnchantMesh mesh : meshList) {
				logger.debug(mesh.texCoords.toString());
			}
		}
		return Lists.transform(meshList,
				new Function<EnchantMesh, EnchantMesh>() {
					@Override
					public EnchantMesh apply(EnchantMesh input) {
						return Utils.transform(input, transform);
					}
				});
	}

	private Obj transform(Obj obj, Part part) {
		final Obj result = new Obj();
		result.setName(obj.getName());

		// アフィン変換
		final Matrix4d affineTransform = new Matrix4d();
		affineTransform.setIdentity();
		// 移動
		affineTransform.mul(translation(part.getMov()[0], part.getMov()[1],
				part.getMov()[2]));
		// 回転
		affineTransform.mul(rotation(part.getRotx(), part.getRoty(),
				part.getRotz()));
		// 拡大
		affineTransform.mul(scale(part.getScal()[0], part.getScal()[1],
				part.getScal()[2]));

		// マイナスに拡大してる軸方向が奇数かどうか判定
		int minus = 0;
		if (part.getScal()[0] < 0) {
			minus++;
		}
		if (part.getScal()[1] < 0) {
			minus++;
		}
		if (part.getScal()[2] < 0) {
			minus++;
		}

		final boolean reverse = (minus % 2 == 1);

		for (Prim origPrim : obj.getPrimitives()) {
			final Prim destPrim = new Prim();

			String destAtrName = convertAtr(origPrim.getAtrName(),
					part.getPaletteName(), obj.getName());
			destPrim.setAtrName(destAtrName);

			for (Vertex vertex : origPrim.getVertices()) {
				final Vertex destVertex = new Vertex();

				// 位置変換
				final Point3d position = new Point3d(vertex.x, vertex.y,
						vertex.z);
				affineTransform.transform(position);
				destVertex.x = position.x;
				destVertex.y = position.y;
				destVertex.z = position.z;

				// 法線ベクトル変換
				final Vector3d normal = new Vector3d(vertex.normalX,
						vertex.normalY, vertex.normalZ);
				affineTransform.transform(normal);
				destVertex.normalX = normal.x;
				destVertex.normalY = normal.y;
				destVertex.normalZ = normal.z;

				// テクスチャ座標は変換なし
				destVertex.u = vertex.u;
				destVertex.v = vertex.v;

				destPrim.getVertices().add(destVertex);
			}

			// 反転している面ではインデックスの順番を逆転
			if (reverse) {
				final List<Vertex> reversed = Lists.newArrayList(Lists
						.reverse(destPrim.getVertices()));
				destPrim.getVertices().clear();
				destPrim.getVertices().addAll(reversed);
			}

			result.getPrimitives().add(destPrim);
		}

		return result;
	}

	private String convertAtr(String genieName, String paletteName,
			String objName) {
		if (genieName == null && paletteName == null) {
			// noop
			return null;
		} else if (genieName != null && paletteName == null) {
			return genieName;
		} else if (genieName == null && paletteName != null) {
			return paletteName;
		} else if (genieName.toLowerCase().startsWith("bodyd")
				|| genieName.toLowerCase().startsWith("bodym")) {
			final String destAtrName = paletteName + "_"
					+ (objName + "_" + genieName).toLowerCase();
			if (!sufConverter.getAtrMap().containsKey(destAtrName)) {
				final Atr destAtr = new Atr();

				final Atr genieAtr = sufConverter.getAtrMap().get(genieName);
				final Color gcol = genieAtr.getCol();
				final Atr paletteAtr = sufConverter.getAtrMap()
						.get(paletteName);
				final Color ocol = paletteAtr.getCol();

				destAtr.setName(destAtrName);
				destAtr.setCol(new Color(ocol.red * gcol.red, ocol.green
						* gcol.green, ocol.blue * gcol.blue));
				destAtr.setAmb(paletteAtr.getAmb());
				destAtr.setDif(paletteAtr.getDif());
				destAtr.setSpc(paletteAtr.getSpc());
				destAtr.setMapSize(paletteAtr.getMapSize());
				destAtr.setOptEmittion(paletteAtr.getOptEmittion());
				sufConverter.putAtr(destAtr);
			}
			return destAtrName;
		} else {
			return genieName;
		}
	}

	private Matrix4d scale(double scaleX, double scaleY, double scaleZ) {
		final Matrix4d result = new Matrix4d();
		result.setIdentity();
		result.m00 = scaleX;
		result.m11 = scaleY;
		result.m22 = scaleZ;
		return result;
	}

	private Matrix4d translation(double movX, double movY, double movZ) {
		Matrix4d result = new Matrix4d();
		result.setIdentity();
		result.setTranslation(new Vector3d(movX, movY, movZ));
		return result;
	}

	private Matrix4d rotation(double rotX, double rotY, double rotZ) {
		final Matrix4d result = new Matrix4d();
		result.setIdentity();
		// z軸
		{
			double theta = rotZ * (2 * Math.PI / 360);
			Quat4d q = new Quat4d(0, 0, Math.sin(theta / 2),
					Math.cos(theta / 2));
			Matrix4d temp = new Matrix4d();
			temp.set(q);
			result.mul(temp);
		}
		// y軸
		{
			double theta = rotY * (2 * Math.PI / 360);
			Quat4d q = new Quat4d(0, Math.sin(theta / 2), 0,
					Math.cos(theta / 2));
			Matrix4d temp = new Matrix4d();
			temp.set(q);
			result.mul(temp);
		}
		// x軸
		{
			double theta = rotX * (2 * Math.PI / 360);
			Quat4d q = new Quat4d(Math.sin(theta / 2), 0, 0,
					Math.cos(theta / 2));
			Matrix4d temp = new Matrix4d();
			temp.set(q);
			result.mul(temp);
		}
		return result;
	}

}
