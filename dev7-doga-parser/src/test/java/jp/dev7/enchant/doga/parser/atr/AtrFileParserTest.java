package jp.dev7.enchant.doga.parser.atr;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jp.dev7.enchant.doga.parser.Props;
import jp.dev7.enchant.doga.parser.atr.autogen.ParseException;
import jp.dev7.enchant.doga.parser.atr.data.Atr;
import junit.framework.TestCase;

public class AtrFileParserTest extends TestCase {

    public void testParse() throws Exception {
        AtrFileParser.parse(new File(Props.dataDir(),
                "l3_samp/connection/human/robot/heavyms/heavyms.atr"));
        AtrFileParser.parse(new File(Props.dataDir(),
                "l3_samp/connection/human/robot/kage/kage.atr"));

        File partsDir = Props.commonDir();
        parse(partsDir);

        List<Atr> r9nd = AtrFileParser.parse(new File(
                "src/test/resources/r9nd.atr"));
        System.out.println("size = " + r9nd.size());
        for (Atr atr : r9nd) {
            System.out.println("     atr name = " + atr.getName());
        }
    }

    private void parse(File dir) throws ParseException, IOException {
        for (File f : dir.listFiles()) {
            if (f.isFile() && f.getName().toLowerCase().endsWith(".atr")) {
                System.out.println(f);
                AtrFileParser.parse(f);
            } else if (f.isDirectory()) {
                parse(f);
            }
        }
    }
}
