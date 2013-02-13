package jp.dev7.enchant.doga.parser.l3p;

import java.io.File;

import jp.dev7.enchant.doga.parser.Props;
import jp.dev7.enchant.doga.parser.atr.data.Atr;
import jp.dev7.enchant.doga.parser.l3p.data.L3p;
import junit.framework.TestCase;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class L3pFileParserTest extends TestCase {

    public void test43() throws Exception {
        L3pFileParser parser = new L3pFileParser();
        parser.parse(new File("src/test/resources/66ace1_8.L3P"));
    }

    public void test42() throws Exception {
        Function<Atr, String> atrToName = new Function<Atr, String>() {
            @Override
            public String apply(Atr input) {
                return input.getName();
            }
        };

        L3pFileParser parser = new L3pFileParser();
        L3p raiden = parser.parse(new File("src/test/resources/test.L3P"));
        Collections2.transform(raiden.getPalette().values(), atrToName)
                .contains("#01 あお");
    }

    public void testParse() throws Exception {

        L3pFileParser parser1 = new L3pFileParser();
        L3p result1 = parser1.parse(new File("src/test/resources/test.L3P"));

        System.out.println(result1);

        L3pFileParser parser2 = new L3pFileParser();
        L3p result2 = parser2.parse(new File("src/test/resources/s.L3P"));

        System.out.println(result2);

        L3pFileParser parser3 = new L3pFileParser();
        L3p result3 = parser3.parse(new File(
                "src/test/resources/R-9ND-Daishi-The-Rubicon.L3P"));
        assertEquals(result3.getObjects().get(0).getSufFileName(),
                "P3\\P305.suf".toLowerCase());

        System.out.println(result3);

        File partsDir = Props.dataDir();
        parse(partsDir);

    }

    private void parse(File dir) throws Exception {
        for (File f : dir.listFiles()) {
            if (f.isFile() && f.getName().toLowerCase().endsWith(".l3p")) {
                System.out.println(f);
                L3pFileParser parser = new L3pFileParser();
                parser.parse(f);
            } else if (f.isDirectory()) {
                parse(f);
            }
        }
    }
}
