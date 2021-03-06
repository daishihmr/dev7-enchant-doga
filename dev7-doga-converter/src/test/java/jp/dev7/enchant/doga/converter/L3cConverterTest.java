package jp.dev7.enchant.doga.converter;

import java.io.File;
import java.io.PrintWriter;

import jp.dev7.enchant.doga.converter.data.enchantjs.EnchantArticulated;
import jp.dev7.enchant.doga.parser.util.Props;
import junit.framework.TestCase;
import net.arnx.jsonic.JSON;

public class L3cConverterTest extends TestCase {

    public void testConvert() throws Exception {
        L3cConverter conv = new L3cConverter();
        EnchantArticulated result = conv.convert(new File(Props.dataDir(),
                "l3_samp/connection/human/robot/heavyms.l3c"));

        JSON.encode(result, new PrintWriter("target/result.js"), true);
    }

}
