package jp.dev7.enchant.doga.converter;

import java.io.File;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import jp.dev7.enchant.doga.converter.data.EnchantMesh;
import jp.dev7.enchant.doga.parser.AtrFileParser;
import jp.dev7.enchant.doga.parser.FscFileParser;
import jp.dev7.enchant.doga.parser.L2pFileParser;
import jp.dev7.enchant.doga.parser.L3pFileParser;
import jp.dev7.enchant.doga.parser.SufFileParser;
import jp.dev7.enchant.doga.parser.data.Atr;
import jp.dev7.enchant.doga.parser.data.Color;
import jp.dev7.enchant.doga.parser.data.Obj;
import jp.dev7.enchant.doga.parser.data.Prim;
import jp.dev7.enchant.doga.parser.data.Suf;
import jp.dev7.enchant.doga.parser.data.Unit;
import jp.dev7.enchant.doga.parser.data.UnitObj;
import jp.dev7.enchant.doga.parser.data.Vertex;
import jp.dev7.enchant.doga.util.Utils;
import net.arnx.jsonic.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class UnitConverter {

    private static final Logger LOG = LoggerFactory
            .getLogger(UnitConverter.class);

    public String convertToJson(File file) throws Exception {
        final StringWriter result = new StringWriter();
        convertAndWriteJson(file, result);
        return result.toString();
    }

    public String convertToJson(File file, Matrix4d transform) throws Exception {
        final StringWriter result = new StringWriter();
        convertAndWriteJson(file, transform, result);
        return result.toString();
    }

    public void convertAndWriteJson(File file, OutputStream out)
            throws Exception {
        JSON.encode(convert(file), out, false);
    }

    public void convertAndWriteJson(File file, Matrix4d transform,
            OutputStream out) throws Exception {
        JSON.encode(convert(file, transform), out, false);
    }

    public void convertAndWriteJson(File file, Appendable appendable)
            throws Exception {
        JSON.encode(convert(file), appendable, false);
    }

    public void convertAndWriteJson(File file, Matrix4d transform,
            Appendable appendable) throws Exception {
        JSON.encode(convert(file, transform), appendable, false);
    }

    public List<EnchantMesh> convert(File file) throws Exception {
        return convert(file, Utils.getIdentity());
    }

    public List<EnchantMesh> convert(final File file, final Matrix4d transform)
            throws Exception {
        final Unit data;
        if (file.getName().toLowerCase().endsWith(".fsc")) {
            data = new FscFileParser().parse(file);
        } else if (file.getName().toLowerCase().endsWith(".l2p")) {
            data = new L2pFileParser().parse(file);
        } else if (file.getName().toLowerCase().endsWith(".l3p")) {
            data = new L3pFileParser().parse(file);
        } else {
            throw new IllegalArgumentException("unknown file extension ("
                    + file.getName() + ")");
        }
        return convert(data, transform, file);
    }

    public List<EnchantMesh> convert(final Unit data, final Matrix4d transform,
            File baseFile) throws Exception {
        final SufConverter sufConverter = new SufConverter();
        sufConverter.loadGenieAtr();
        final SufFileParser sufFileParser = new SufFileParser();

        sufConverter.putAllAtr(data.getPalette());
        LOG.debug("atrMap = " + sufConverter.getAtrMap());

        final Suf dest = new Suf();
        int i = 0;
        for (final UnitObj part : data.getObjects()) {
            final List<Obj> destObjects = Lists.newArrayList();

            LOG.debug("part" + (++i) + " : " + part.getName());
            File sufFile = Utils.findSufFile(part.getSufFileName(), baseFile);
            if (sufFile == null) {
                continue;
            }

            final Suf orig = sufFileParser.parse(sufFile);
            LOG.debug("load SUFfile " + sufFile);
            if (LOG.isDebugEnabled()) {
                int cnt = 0;
                for (Obj obj : orig.getObjects()) {
                    for (Prim prim : obj.getPrimitives()) {
                        cnt += prim.getVertices().size();
                    }
                }
                LOG.debug("number of vertex = " + cnt);
            }
            File atrFile = new File(sufFile.getAbsolutePath().replace(".suf",
                    ".atr"));
            if (atrFile.exists()) {
                List<Atr> atrs = AtrFileParser.parse(atrFile);
                sufConverter.putAllAtr(atrs);
                LOG.debug("load ATRfile " + atrFile);
            }

            int j = 0;
            for (Obj origObj : orig.getObjects()) {
                LOG.debug("    convert obj(" + j + ") begin");
                if (LOG.isDebugEnabled()) {
                    int cnt = 0;
                    for (Prim p : origObj.getPrimitives()) {
                        cnt += p.getVertices().size();
                    }
                    LOG.debug("    number of vertex = " + cnt);
                }

                Obj destObj = transform(origObj, part, sufConverter);

                LOG.debug("    convert obj(" + j + ") end");
                if (LOG.isDebugEnabled()) {
                    int cnt = 0;
                    for (Prim p : destObj.getPrimitives()) {
                        cnt += p.getVertices().size();
                    }
                    LOG.debug("    number of vertex = " + cnt);
                    j++;
                }

                destObjects.add(destObj);
            }

            LOG.debug("convert SUF successful");
            if (LOG.isDebugEnabled()) {
                int cnt = 0;
                for (Obj obj : destObjects) {
                    for (Prim prim : obj.getPrimitives()) {
                        cnt += prim.getVertices().size();
                    }
                }
                LOG.debug("number of vertex = " + cnt);
            }

            dest.getObjects().addAll(destObjects);
        }

        final List<EnchantMesh> meshList = sufConverter.convert(dest);
        if (LOG.isDebugEnabled()) {
            LOG.debug("texture coords");
            for (EnchantMesh mesh : meshList) {
                LOG.debug(mesh.texCoords.toString());
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

    private Obj transform(Obj obj, UnitObj part, SufConverter sufConverter) {
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
                    part.getPaletteName(), obj.getName(), sufConverter);
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
            String objName, SufConverter sufConverter) {
        final Map<String, Atr> atrMap = sufConverter.getAtrMap();
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
            if (!atrMap.containsKey(destAtrName)) {
                final Atr newAtr = new Atr();

                final Atr genieAtr = atrMap.get(genieName);
                final Color gcol = genieAtr.getCol();
                final Atr paletteAtr = atrMap.get(paletteName);
                final Color ocol = paletteAtr.getCol();

                newAtr.setName(destAtrName);
                newAtr.setCol(new Color(ocol.red * gcol.red, ocol.green
                        * gcol.green, ocol.blue * gcol.blue));
                newAtr.setAmb(paletteAtr.getAmb());
                newAtr.setDif(paletteAtr.getDif());
                newAtr.setSpc(paletteAtr.getSpc());
                newAtr.setMapSize(paletteAtr.getMapSize());
                newAtr.setOptEmittion(paletteAtr.getOptEmittion());
                sufConverter.putAtr(newAtr);
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
