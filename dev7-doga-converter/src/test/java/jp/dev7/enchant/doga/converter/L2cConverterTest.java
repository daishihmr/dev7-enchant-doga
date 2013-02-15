package jp.dev7.enchant.doga.converter;

import java.io.File;

import jp.dev7.enchant.doga.parser.util.Props;
import junit.framework.TestCase;

public class L2cConverterTest extends TestCase {

    public void test() throws Exception {
        L2cConverter conv = new L2cConverter();
        conv.convertAndWriteJson(new File(Props.dataDir(),
                "connect/l2_samp/demodata/walker_a.l2c"), System.out);

    }

}
