package jp.dev7.enchant.doga.converter.l3p;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import jp.dev7.enchant.doga.converter.EnchantMesh;
import jp.dev7.enchant.doga.converter.l3p.L3pConverter;
import jp.dev7.enchant.doga.parser.l3p.L3pFileParser;
import jp.dev7.enchant.doga.parser.l3p.data.L3p;
import junit.framework.TestCase;
import net.arnx.jsonic.JSON;

public class L3pConverterTest extends TestCase {

    public void testConvert() throws Exception {

        String l3p = "src/test/resources/fighter.L3P";

        {
            L3p p = new L3pFileParser().parse(new File(l3p));
            assertEquals(p.getObjects().get(0).getSufFileName(),
                    "mecha\\sfplane\\F108.suf".toLowerCase());
        }

        List<EnchantMesh> data = new L3pConverter().convert(new File(l3p));
        for (EnchantMesh mesh : data) {
            System.out.println(mesh.texCoords);
        }

        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
                "target/fighter.json"), "UTF-8");
        JSON.encode(data, out, true);
        out.flush();
        out.close();

        assertNotNull(data);
        assertFalse(data.isEmpty());

        for (EnchantMesh m : data) {
            assertTrue(m.indices.size() % 3 == 0);
            int vertexNum = m.vertices.size() / 3;
            for (int index : m.indices) {
                assertTrue(index < vertexNum);
            }
        }

    }

}
