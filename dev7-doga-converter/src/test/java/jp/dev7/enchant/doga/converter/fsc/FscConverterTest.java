package jp.dev7.enchant.doga.converter.fsc;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import jp.dev7.enchant.doga.converter.EnchantMesh;
import junit.framework.TestCase;
import net.arnx.jsonic.JSON;

import com.google.common.base.Function;

public class FscConverterTest extends TestCase {

    public void testConvert() throws Exception {
        FscConverter conv = new FscConverter();
        List<EnchantMesh> result = conv.convert(new File(
                "src/test/resources/l1_samp/demodata/batlship/battle1.fsc"));

        JSON.encode(result, new PrintWriter("target/fsc_test.js"), true);
    }

    public void testAll() throws Exception {
        final FscConverter conv = new FscConverter();
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
