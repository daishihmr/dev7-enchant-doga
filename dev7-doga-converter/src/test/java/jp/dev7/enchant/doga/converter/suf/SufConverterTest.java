package jp.dev7.enchant.doga.converter.suf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import jp.dev7.enchant.doga.converter.EnchantMesh;
import jp.dev7.enchant.doga.parser.atr.data.Atr;
import jp.dev7.enchant.doga.parser.atr.data.Color;
import jp.dev7.enchant.doga.parser.suf.SufFileParser;
import jp.dev7.enchant.doga.parser.suf.data.Obj;
import jp.dev7.enchant.doga.parser.suf.data.Prim;
import jp.dev7.enchant.doga.parser.suf.data.Suf;
import jp.dev7.enchant.doga.parser.suf.data.Vertex;
import junit.framework.TestCase;
import net.arnx.jsonic.JSON;

import com.google.common.collect.Lists;

public class SufConverterTest extends TestCase {

    public void testFile() throws Exception {
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
                "target/test.js"), "UTF-8");

        final Suf suf = SufFileParser.parseSufAtr(new File(
                "src/test/resources/test.SUF"));
        final SufConverter converter = new SufConverter();

        final List<EnchantMesh> meshs = converter.convert(suf);
        out.append("data=");
        JSON.encode(meshs, out, false);
        out.append(";");

        out.flush();
        out.close();
    }

    public void testConv() throws Exception {

        Obj o = new Obj();
        o.setName("テスト");

        // prim1
        {
            Prim p = new Prim();
            {
                Vertex v = new Vertex();
                v.x = 0;
                v.y = 0;
                v.z = 0;
                v.normalX = 0;
                v.normalY = 2000.0;
                v.normalZ = 0.0;
                v.u = 0;
                v.v = 0;
                p.getVertices().add(v);
            }
            {
                Vertex v = new Vertex();
                v.x = 3.0 * 2000.0;
                v.y = 0;
                v.z = 0;
                v.normalX = 0;
                v.normalY = 0;
                v.normalZ = 0.0;
                v.u = 0;
                v.v = 0;
                p.getVertices().add(v);
            }
            {
                Vertex v = new Vertex();
                v.x = 0;
                v.y = 4.0 * 2000.0;
                v.z = 0;
                v.normalX = 0;
                v.normalY = 0;
                v.normalZ = 0.0;
                v.u = 255;
                v.v = 255;
                p.getVertices().add(v);
            }
            p.setAtrName("atr1");
            o.getPrimitives().add(p);
        }

        List<Atr> atrs = Lists.newArrayList();
        // atr1
        {
            Atr atr = new Atr();
            atr.setName("atr1");
            atr.setCol(new Color(1.0, 0.8, 0.6));
            atr.setAmb(0.5);
            atr.setTra(1.0);
            atr.setColorMap1("test1.png");
            atr.setColorMap2("test2.png");
            atr.setMapSize(new double[] { 63.750, 63.750, 191.250, 191.250 });
            atrs.add(atr);
        }

        SufConverter converter = new SufConverter();
        converter.putAllAtr(atrs);
        EnchantMesh result = converter.convert(o).get(0);
        JSON.encode(result, (OutputStream) System.out, false);

        assertEquals(0.0, result.getVertices().get(0)); // x
        assertEquals(0.0, result.getVertices().get(1)); // y
        assertEquals(0.0, result.getVertices().get(2)); // z
        assertEquals(4.0, result.getVertices().get(3)); // x
        assertEquals(0.0, result.getVertices().get(4)); // y
        assertEquals(0.0, result.getVertices().get(5)); // z
        assertEquals(0.0, result.getVertices().get(6)); // x
        assertEquals(0.0, result.getVertices().get(7)); // y
        assertEquals(3.0, result.getVertices().get(8)); // z

        assertEquals(0.0, result.getNormals().get(0)); // normal
        assertEquals(1.0, result.getNormals().get(1)); // normal

        assertEquals(1.0 * 0.5, result.getTexture().getAmbient().get(0)); // r
        assertEquals(0.8 * 0.5, result.getTexture().getAmbient().get(1)); // g
        assertEquals(0.6 * 0.5, result.getTexture().getAmbient().get(2)); // b
        assertEquals(1.0, result.getTexture().getAmbient().get(3)); // a

        assertEquals(-0.5, result.getTexCoords().get(0)); // u
        assertEquals(1.5, result.getTexCoords().get(1)); // v
        assertEquals(1.5, result.getTexCoords().get(2)); // u
        assertEquals(-0.5, result.getTexCoords().get(3)); // v

        assertEquals("test1.png", result.getTexture().src);

    }

    public void testConv2() throws Exception {

        Obj o = new Obj();
        o.setName("テスト");

        // prim1
        {
            Prim p = new Prim();
            {
                Vertex v = new Vertex();
                v.x = 0;
                v.y = 0;
                v.z = 0;
                v.normalX = 0;
                v.normalY = 2000.0;
                v.normalZ = 0.0;
                v.u = 0;
                v.v = 0;
                p.getVertices().add(v);
            }
            {
                Vertex v = new Vertex();
                v.x = 3.0 * 2000.0;
                v.y = 0;
                v.z = 0;
                v.normalX = 0;
                v.normalY = 0;
                v.normalZ = 0.0;
                v.u = 0;
                v.v = 0;
                p.getVertices().add(v);
            }
            {
                Vertex v = new Vertex();
                v.x = 0;
                v.y = 4.0 * 2000.0;
                v.z = 0;
                v.normalX = 0;
                v.normalY = 0;
                v.normalZ = 0.0;
                v.u = 255;
                v.v = 255;
                p.getVertices().add(v);
            }
            {
                Vertex v = new Vertex();
                v.x = 10;
                v.y = 4.0 * 2000.0;
                v.z = 0;
                v.normalX = 0;
                v.normalY = 0;
                v.normalZ = 0.0;
                v.u = 255;
                v.v = 255;
                p.getVertices().add(v);
            }
            p.setAtrName("atr1");
            o.getPrimitives().add(p);
        }

        List<Atr> atrs = Lists.newArrayList();
        // atr1
        {
            Atr atr = new Atr();
            atr.setName("atr1");
            atr.setCol(new Color(1.0, 0.8, 0.6));
            atr.setAmb(0.5);
            atr.setTra(1.0);
            atr.setColorMap1("test1.png");
            atr.setColorMap2("test2.png");
            atr.setMapSize(new double[] { 63.750, 63.750, 191.250, 191.250 });
            atrs.add(atr);
        }

        SufConverter converter = new SufConverter();
        converter.putAllAtr(atrs);
        EnchantMesh result = converter.convert(o).get(0);

        assertEquals(result.vertices.size(), 3 * 4);
        assertEquals(result.indices.size(), 6);
    }
}
