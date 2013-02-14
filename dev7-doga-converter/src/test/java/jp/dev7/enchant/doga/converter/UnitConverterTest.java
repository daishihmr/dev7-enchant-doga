package jp.dev7.enchant.doga.converter;

import java.io.File;
import java.io.FileOutputStream;

import jp.dev7.enchant.doga.converter.UnitConverter;
import jp.dev7.enchant.doga.parser.util.FileTreeUtil;
import jp.dev7.enchant.doga.parser.util.Props;
import junit.framework.TestCase;

import com.google.common.base.Function;

public class UnitConverterTest extends TestCase {

    public void testConvertFsc() throws Exception {
        UnitConverter conv = new UnitConverter();

        FileOutputStream out = new FileOutputStream(new File(
                "target/test.fsc.js"));

        conv.convertAndWriteJson(new File(Props.dataDir(),
                "mecha/l1_samp/demodata/batlship/battle1.fsc"), out);
    }

    public void testConvertL2p() throws Exception {
        UnitConverter conv = new UnitConverter();

        FileOutputStream out = new FileOutputStream(new File(
                "target/test.l2p.js"));

        conv.convertAndWriteJson(new File(Props.dataDir(),
                "mecha/l2_samp/demodata/walker/w_a_body.l2p"), out);
    }

    public void testConvertL3p() throws Exception {
        UnitConverter conv = new UnitConverter();

        FileOutputStream out = new FileOutputStream(new File(
                "target/test.l3p.js"));

        conv.convertAndWriteJson(new File(Props.dataDir(),
                "l3_samp/connection/animal/zdnnhund.l3p"), out);
    }

    public void testAllFsc() throws Exception {
        final UnitConverter conv = new UnitConverter();
        FileTreeUtil.scanDir(Props.dataDir(), new Function<File, Void>() {
            @Override
            public Void apply(File input) {
                if (input.getName().toLowerCase().endsWith(".fsc")
                        || input.getName().toLowerCase().endsWith(".l2p")
                        || input.getName().toLowerCase().endsWith(".l3p")) {
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
}
