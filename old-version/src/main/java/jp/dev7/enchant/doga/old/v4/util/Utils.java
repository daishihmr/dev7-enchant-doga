package jp.dev7.enchant.doga.old.v4.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import jp.dev7.enchant.doga.old.v4.converter.EnchantMesh;
import jp.dev7.enchant.doga.old.v4.converter.EnchantTexture;
import jp.dev7.enchant.doga.old.v4.parser.Props;
import jp.dev7.enchant.doga.old.v4.parser.atr.data.Atr;
import jp.dev7.enchant.doga.old.v4.parser.atr.data.Color;
import jp.dev7.enchant.doga.old.v4.parser.suf.data.Prim;
import jp.dev7.enchant.doga.old.v4.parser.suf.data.Vertex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class Utils {

	private static Logger logger = LoggerFactory.getLogger(Utils.class);

	/**
	 * 多角形を三角形に分割.
	 */
	public static List<Prim> splitToTriangle(Prim prim) {
		if (prim.getVertices().size() < 3) {
			return Collections.emptyList();
		} else if (prim.getVertices().size() == 3) {
			return Lists.newArrayList(prim);
		}

		final List<Prim> result = Lists.newArrayList();
		final Vertex head = prim.getVertices().get(0);
		for (int i = 1; i < prim.getVertices().size() - 1; i++) {
			final Prim tri = new Prim();
			tri.setAtrName(prim.getAtrName());
			tri.getVertices().add(head);
			tri.getVertices().add(prim.getVertices().get(i));
			tri.getVertices().add(prim.getVertices().get(i + 1));
			result.add(tri);
		}

		return result;
	}

	/**
	 * DoGA式マッピング座標をGL式に変換する.
	 * 
	 * @param mapsize
	 *            atrのmapsize
	 * @param uv
	 *            テクスチャ座標uとv
	 */
	public static double[] convertTexCoords(double[] mapsize, double[] uv) {
		if (Arrays.toString(mapsize).equals("[0.0, 0.0, 255.0, 255.0]")
				&& Arrays.toString(uv).equals("[0.0, 0.0]")) {
			return new double[] { 0, 0 };
		}
		if (logger.isDebugEnabled()) {
			logger.debug("テクスチャ座標を変換");
			logger.debug("mapsize = " + Arrays.toString(mapsize));
			logger.debug("uv = " + Arrays.toString(uv));
		}
		// mapsize(umin vmin umax vmax)のとき、
		double umin = mapsize[0];
		double vmin = mapsize[1];
		double umax = mapsize[2];
		double vmax = mapsize[3];
		// u倍率 = (umax-umin) / 255
		double ru = (umax - umin) / 255;
		// v倍率 = (vmax-vmin) / 255
		double rv = (vmax - vmin) / 255;
		// uズレ = umin/ru
		double uz = umin / ru;
		// vズレ = vmin/rv
		double vz = vmin / rv;
		// bu = u / ru
		double bu = uv[0] / ru;
		// bv = v / rv
		double bv = uv[1] / rv;
		double[] result = new double[2];
		// U = (bu - uz) / 255
		result[0] = (bu - uz) / 255;
		// V = (bv - vz) / 255
		result[1] = (bv - vz) / 255;
		// Vは反転(0-1 → 1-0)
		result[1] = result[1] * -1 + 1;

		// System.out.println("u=" + v.u);
		// System.out.println("v=" + v.v);
		// System.out.println("umin=" + umin);
		// System.out.println("umax=" + umax);
		// System.out.println("vmin=" + vmin);
		// System.out.println("vmax=" + vmax);
		// System.out.println("ru=" + ru);
		// System.out.println("rv=" + rv);
		// System.out.println("uz=" + uz);
		// System.out.println("vz=" + vz);
		// System.out.println("U=" + (bu - uz) / 255);
		// System.out.println("V=" + (bv - vz) / 255);
		// System.out.println();

		return result;
	}

	/**
	 * DoGA式アトリビュートをGL式に変換する.
	 */
	public static EnchantTexture convertAttribute(final Atr atr) {
		final EnchantTexture tex = new EnchantTexture();

		tex.name = atr.getName();

		final Color color = atr.getCol();

		// amb
		tex.ambient.add(color.red * atr.getAmb());
		tex.ambient.add(color.green * atr.getAmb());
		tex.ambient.add(color.blue * atr.getAmb());
		tex.ambient.add(1.0);

		// dif
		tex.diffuse.add(color.red * atr.getDif());
		tex.diffuse.add(color.green * atr.getDif());
		tex.diffuse.add(color.blue * atr.getDif());
		tex.diffuse.add(1.0);

		// spc
		final double spcCol = atr.getSpc()[0];
		tex.specular.add(color.red + (1.0 - color.red) * spcCol);
		tex.specular.add(color.green + (1.0 - color.green) * spcCol);
		tex.specular.add(color.blue + (1.0 - color.blue) * spcCol);
		tex.specular.add(1.0);
		// tex.shininess = 30 * atr.getSpc()[1];
		tex.shininess = 20;

		// TODO emission
		tex.emission.add(0.0);
		tex.emission.add(0.0);
		tex.emission.add(0.0);
		tex.emission.add(1.0);

		tex.src = atr.getColorMap1();

		return tex;
	}

	public static <T> List<T> flatten(List<List<T>> in) {
		if (in.size() == 0) {
			return Collections.emptyList();
		} else if (in.size() == 1) {
			return in.get(0);
		} else {
			final List<T> head = in.get(0);
			for (int i = 1; i < in.size(); i++) {
				head.addAll(in.get(i));
			}
			return head;
		}
	}

	public static <K, V> V getOrElse(Map<K, V> map, K key, V whenNotContain) {
		if (map.containsKey(key)) {
			return map.get(key);
		} else {
			return whenNotContain;
		}
	}

	public static File dogaPartsFile(String path, File baseFile)
			throws IOException {
		logger.debug("dogaPartsFile: " + path);
		if (path == null || path.equals("")) {
			return null;
		}
		path = path.toLowerCase();
		if (File.separatorChar != '\\') {
			while (path.contains("\\")) {
				path = path.replace('\\', File.separatorChar);
			}
		}
		logger.debug("探す, " + path);

		// 絶対パス？
		File cur = new File(path);
		logger.debug("    " + cur.getAbsolutePath() + " ?");
		if (cur.exists()) {
			return cur;
		}

		// 相対パス？
		if (baseFile != null) {
			File rel = new File(baseFile.getParentFile(), path);
			logger.debug("    " + rel.getAbsolutePath() + " ?");
			if (rel.exists()) {
				return rel;
			}
		}

		// commonの中？
		File common = new File(Props.commonDir(), "parts/" + path);
		logger.debug("    " + common.getAbsolutePath() + "?");
		if (common.exists()) {
			return common;
		}

		logger.warn("ファイルが見つかりません. " + path);
		return null;
	}

	/**
	 * Meshデータをアフィン変換する.
	 */
	public static EnchantMesh transform(EnchantMesh mesh, Matrix4d transform) {
		final EnchantMesh result = mesh.clone();

		// 面方向反転判定
		boolean reverse = false;
		int minus = 0;
		if (transform.m00 < 0) {
			minus++;
		}
		if (transform.m11 < 0) {
			minus++;
		}
		if (transform.m22 < 0) {
			minus++;
		}
		if (minus % 2 == 1) {
			reverse = true;
		}

		// 面方向反転
		if (reverse) {
			for (int i = 0; i < result.indices.size(); i += 3) {
				int temp = result.indices.get(i + 0);
				result.indices.set(i + 0, result.indices.get(i + 2));
				result.indices.set(i + 2, temp);
			}
		}

		if (isIdentity(transform)) {
			return result;
		}

		// vertices
		for (int i = 0; i < result.vertices.size(); i += 3) {
			Point3d v = new Point3d(result.vertices.get(i + 0),
					result.vertices.get(i + 1), result.vertices.get(i + 2));
			transform.transform(v);
			result.vertices.set(i + 0, v.x);
			result.vertices.set(i + 1, v.y);
			result.vertices.set(i + 2, v.z);
		}
		// normals
		for (int i = 0; i < result.normals.size(); i += 3) {
			Vector3d v = new Vector3d(result.normals.get(i + 0),
					result.normals.get(i + 1), result.normals.get(i + 2));
			transform.transform(v);
			result.normals.set(i + 0, v.x);
			result.normals.set(i + 1, v.y);
			result.normals.set(i + 2, v.z);
		}
		return result;
	}

	public static boolean isIdentity(Matrix4d mat4) {
		return (mat4.m00 == 1 && mat4.m01 == 0 && mat4.m02 == 0 && mat4.m03 == 0)//
				&& (mat4.m10 == 0 && mat4.m11 == 1 && mat4.m12 == 0 && mat4.m13 == 0)//
				&& (mat4.m20 == 0 && mat4.m21 == 0 && mat4.m22 == 1 && mat4.m23 == 0)//
				&& (mat4.m30 == 0 && mat4.m31 == 0 && mat4.m32 == 0 && mat4.m33 == 1);
	}

	public static Matrix4d getIdentity() {
		Matrix4d result = new Matrix4d();
		result.setIdentity();
		return result;
	}
}
