package jp.dev7.enchant.doga.converter.fsc;

import java.io.File;
import java.util.List;

import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import jp.dev7.enchant.doga.converter.EnchantMesh;
import jp.dev7.enchant.doga.converter.suf.SufConverter;
import jp.dev7.enchant.doga.parser.atr.AtrFileParser;
import jp.dev7.enchant.doga.parser.atr.data.Atr;
import jp.dev7.enchant.doga.parser.fsc.FscFileParser;
import jp.dev7.enchant.doga.parser.fsc.data.Fsc;
import jp.dev7.enchant.doga.parser.fsc.data.FscObj;
import jp.dev7.enchant.doga.parser.suf.SufFileParser;
import jp.dev7.enchant.doga.parser.suf.data.Obj;
import jp.dev7.enchant.doga.parser.suf.data.Prim;
import jp.dev7.enchant.doga.parser.suf.data.Suf;
import jp.dev7.enchant.doga.parser.suf.data.Vertex;
import jp.dev7.enchant.doga.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class FscConverter {

    private final Logger logger = LoggerFactory.getLogger(FscConverter.class);
    private final SufConverter sufConverter;

    public FscConverter() throws Exception {
        sufConverter = new SufConverter();
        sufConverter.loadGenieAtr("genie_wh.atr");
    }

    public List<EnchantMesh> convert(File file) throws Exception {
        final SufFileParser sufFileParser = new SufFileParser();
        final Fsc data = new FscFileParser().parse(file);

        final Suf dest = new Suf();
        int i = 0;
        for (final FscObj part : data.getObjects()) {
            final List<Obj> destObjects = Lists.newArrayList();

            logger.debug("パーツ" + (++i) + " : " + part.getName());
            File sufFile = Utils.dogaPartsFile(part.getSufFileName(), file);
            if (sufFile == null) {
                continue;
            }

            final Suf orig = sufFileParser.parse(sufFile);
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
        return meshList;
    }

    private Obj transform(Obj obj, FscObj part) {
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

            destPrim.setAtrName(origPrim.getAtrName());

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
