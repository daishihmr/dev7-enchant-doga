package jp.dev7.enchant.doga.converter;

import static jp.dev7.enchant.doga.util.Utils.*;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.vecmath.Vector3d;

import jp.dev7.enchant.doga.converter.data.EnchantMesh;
import jp.dev7.enchant.doga.parser.AtrFileParser;
import jp.dev7.enchant.doga.parser.atr.autogen.ParseException;
import jp.dev7.enchant.doga.parser.data.Atr;
import jp.dev7.enchant.doga.parser.data.Color;
import jp.dev7.enchant.doga.parser.data.Obj;
import jp.dev7.enchant.doga.parser.data.Prim;
import jp.dev7.enchant.doga.parser.data.Suf;
import jp.dev7.enchant.doga.parser.data.Vertex;
import jp.dev7.enchant.doga.parser.util.Props;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class SufConverter {
    public static final double C_RATE = 1.0 / 2000.0;

    private static final Atr DEFAULT_ATR = new Atr();
    static {
        DEFAULT_ATR.setName("");
        DEFAULT_ATR.setCol(new Color(1.0, 1.0, 1.0));
        DEFAULT_ATR.setAmb(0.3);
        DEFAULT_ATR.setDif(0.7);
    }

    private final Logger logger = LoggerFactory.getLogger(SufConverter.class);

    private final Map<String, Atr> atrMap = Maps.newHashMap();

    public SufConverter() throws IOException, ParseException {
        loadGenieAtr("genienm.atr");
    }

    public void loadGenieAtr(String atrFileName) throws IOException,
            ParseException {
        final File genieAtr = new File(Props.commonDir(), "atr/" + atrFileName);
        final List<Atr> genie = AtrFileParser.parse(genieAtr);
        putAllAtr(genie);
    }

    public void putAtr(Atr atr) {
        logger.debug("アトリビュート追加 '" + atr.getName() + "' : [" + atr + "]");
        atrMap.put(atr.getName().toLowerCase(), atr);
    }

    public void putAllAtr(Iterable<Atr> atrs) {
        for (Atr atr : atrs) {
            putAtr(atr);
        }
    }

    public void putAllAtr(Map<String, Atr> atrMap) {
        putAllAtr(atrMap.values());
    }

    public List<EnchantMesh> convert(Suf suf) {
        putAllAtr(suf.getAtrMap());
        return convert(suf.getObjects());
    }

    public List<EnchantMesh> convert(List<Obj> objs, Collection<Atr> atrs) {
        putAllAtr(atrs);
        return convert(objs);
    }

    public List<EnchantMesh> convert(Obj obj, Collection<Atr> atrs) {
        putAllAtr(atrs);
        return convert(obj);
    }

    public List<EnchantMesh> convert(List<Obj> objs) {
        final Obj all = new Obj();
        all.setName("all");
        for (Obj obj : objs) {
            all.getPrimitives().addAll(obj.getPrimitives());
        }
        return convert(all);
    }

    public List<EnchantMesh> convert(Obj obj) {
        final List<EnchantMesh> result = Lists.newArrayList();

        // atr名毎にprimをまとめる
        logger.debug("atr名毎にprimをまとめる");
        final Map<String, List<Prim>> atrPrimsMap = Maps.newHashMap();
        {
            for (Prim prim : obj.getPrimitives()) {
                // 法線ベクトルのない頂点には法線ベクトル生成
                genNormal(prim);

                final List<Prim> prims;
                if (!atrPrimsMap.containsKey(prim.getAtrName())) {
                    prims = Lists.newArrayList();
                    atrPrimsMap.put(prim.getAtrName(), prims);
                } else {
                    prims = atrPrimsMap.get(prim.getAtrName());
                }

                prims.add(prim);
            }
        }

        if (logger.isDebugEnabled()) {
            for (String atr : atrPrimsMap.keySet()) {
                logger.debug("atr = " + atr);
            }
        }

        logger.debug("材質毎にMeshに変換していく");
        for (String atrName : atrPrimsMap.keySet()) {
            logger.debug("AtrName = " + atrName);
            final EnchantMesh mesh = new EnchantMesh();
            mesh.setName(atrName);

            final List<Prim> prims = atrPrimsMap.get(atrName);
            if (logger.isDebugEnabled()) {
                logger.debug("テクスチャ座標デバッグ");
                for (Prim prim : prims) {
                    for (Vertex vertex : prim.getVertices()) {
                        logger.debug("u = " + vertex.u + ", v = " + vertex.v);
                    }
                }
            }
            final Atr atr;
            if (atrMap.containsKey(atrName)) {
                atr = atrMap.get(atrName);
            } else {
                logger.warn("Atrが見つからない. " + atrName);
                atr = DEFAULT_ATR;
            }

            // 頂点配列作成
            final Vertex[] vertices = toArray(prims);

            for (Vertex v : vertices) {
                // 位置座標を変換
                mesh.vertices.add(v.y * C_RATE);
                mesh.vertices.add(v.z * C_RATE);
                mesh.vertices.add(v.x * C_RATE);

                // 法線ベクトルの長さを正規化
                Vector3d normal = new Vector3d(v.normalX, v.normalY, v.normalZ);
                normal.normalize();
                if (Double.isNaN(normal.x) || Double.isNaN(normal.y)
                        || Double.isNaN(normal.z)) {
                    logger.debug("法線ベクトルなし");
                    logger.debug("    "
                            + new Vector3d(v.normalX, v.normalY, v.normalZ));
                    mesh.normals.add(0.0);
                    mesh.normals.add(0.0);
                    mesh.normals.add(0.0);
                } else {
                    mesh.normals.add(normal.y);
                    mesh.normals.add(normal.z);
                    mesh.normals.add(normal.x);
                }

                // UV
                double[] uv = uv(atr, new double[] { v.u, v.v });
                mesh.texCoords.add(uv[0]);
                mesh.texCoords.add(uv[1]);

                // 頂点色
                // mesh.colors.add(1.0);
                // mesh.colors.add(1.0);
                // mesh.colors.add(1.0);
                // mesh.colors.add(1.0);
            }

            for (Prim prim : prims) {
                // 多角形ポリゴンを三角形に分割
                for (Prim tri : splitToTriangle(prim)) {
                    int index0 = Arrays.binarySearch(vertices, tri
                            .getVertices().get(0));
                    if (index0 < 0) {
                        logger.error("頂点が見つからない " + tri.getVertices().get(0));
                        continue;
                    }
                    int index1 = Arrays.binarySearch(vertices, tri
                            .getVertices().get(1));
                    if (index1 < 0) {
                        logger.error("頂点が見つからない " + tri.getVertices().get(1));
                        continue;
                    }
                    int index2 = Arrays.binarySearch(vertices, tri
                            .getVertices().get(2));
                    if (index2 < 0) {
                        logger.error("頂点が見つからない " + tri.getVertices().get(2));
                        continue;
                    }
                    if (index0 == index1 || index0 == index2
                            || index1 == index2) {
                        continue;
                    }

                    mesh.indices.add(index0);
                    mesh.indices.add(index1);
                    mesh.indices.add(index2);
                }
            }

            mesh.texture = convertAttribute(atr);

            result.add(mesh);
        }

        return result;
    }

    /**
     * 法線ベクトルを持たないポリゴンに法線ベクトルを設定する.
     * 
     * すでに法線ベクトルが設定されている面には何もしない.<br>
     * 単純にポリゴンの表面に対して垂直な法線ベクトルを生成する.<br>
     * 
     * @param prim
     *            ポリゴン.頂点数が3以上でなければならない
     */
    private void genNormal(Prim prim) {
        if (prim.getVertices().size() < 3) {
            return;
        }
        final Vertex v0 = prim.getVertices().get(0);
        logger.debug("v0 =" + v0);

        long zero = Double.doubleToLongBits(0.0);
        long mZero = Double.doubleToLongBits(-0.0);

        long x = Double.doubleToLongBits(v0.normalX);
        long y = Double.doubleToLongBits(v0.normalY);
        long z = Double.doubleToLongBits(v0.normalZ);
        if ((x != zero && x != mZero) || (y != zero && y != mZero)
                && (z != zero && z != mZero)) {
            return;
        }

        Vector3d normal = new Vector3d();
        for (int i = 1; i < prim.getVertices().size() - 1; i++) {
            final Vertex v1 = prim.getVertices().get(i);
            Vector3d vec1 = new Vector3d(v1.x - v0.x, v1.y - v0.y, v1.z - v0.z);
            final Vertex v2 = prim.getVertices().get(i + 1);
            Vector3d vec2 = new Vector3d(v2.x - v0.x, v2.y - v0.y, v2.z - v0.z);
            Vector3d cross = new Vector3d();
            cross.cross(vec1, vec2);

            normal.add(cross);
        }

        normal.normalize();

        logger.debug("法線ベクトル生成 " + normal);

        for (Vertex v : prim.getVertices()) {
            v.normalX = normal.x;
            v.normalY = normal.y;
            v.normalZ = normal.z;
        }
    }

    private Vertex[] toArray(final List<Prim> prims) {
        final Set<Vertex> temp = Sets.newTreeSet();
        for (Prim prim : prims) {
            for (Vertex v : prim.getVertices()) {
                temp.add(v);
            }
        }
        final Vertex[] result = temp.toArray(new Vertex[0]);
        return result;
    }

    /**
     * DoGA式マッピング座標をGL式に変換する.
     */
    private static double[] uv(final Atr atr, double[] uv) {
        return convertTexCoords(atr.getMapSize(), uv);
    }

    public Map<String, Atr> getAtrMap() {
        return atrMap;
    }

    /**
     * 実験用.
     */
    @SuppressWarnings("unused")
    private int d(double d) {
        DecimalFormat df = new DecimalFormat("#0");
        String fmtd = df.format(d);
        int result = Integer.parseInt(fmtd) / 10 * 10;
        return result;
    }
}
