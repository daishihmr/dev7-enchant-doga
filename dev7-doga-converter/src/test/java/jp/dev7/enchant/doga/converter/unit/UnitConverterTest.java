package jp.dev7.enchant.doga.converter.unit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import jp.dev7.enchant.doga.converter.EnchantMesh;
import jp.dev7.enchant.doga.parser.data.Unit;
import jp.dev7.enchant.doga.parser.l3p.L3pFileParser;
import junit.framework.TestCase;
import net.arnx.jsonic.JSON;

import com.google.common.base.Function;

public class UnitConverterTest extends TestCase {

    public void testConvertFsc() throws Exception {
        UnitConverter conv = new UnitConverter();
        List<EnchantMesh> result = conv.convert(new File(
                "src/test/resources/l1_samp/demodata/batlship/battle1.fsc"));

        JSON.encode(result, new PrintWriter("target/fsc_test.js"), true);
    }

    public void testAllFsc() throws Exception {
        final UnitConverter conv = new UnitConverter();
        scanDir(new File("src/test/resources/l1_samp"),
                new Function<File, Void>() {
                    @Override
                    public Void apply(File input) {
                        if (input.getName().toLowerCase().endsWith(".fsc")) {
                            try {
                                conv.convert(input);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }
                });
    }

    public void testConvertL3p() throws Exception {

        String l3p = "src/test/resources/fighter.L3P";

        {
            Unit p = new L3pFileParser().parse(new File(l3p));
            assertEquals(p.getObjects().get(0).getSufFileName(),
                    "mecha\\sfplane\\F108.suf".toLowerCase());
        }

        List<EnchantMesh> data = new UnitConverter().convert(new File(l3p));
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

    private static void scanDir(File file, Function<File, Void> f) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                scanDir(child, f);
            }
        } else {
            f.apply(file);
        }
    }
}
