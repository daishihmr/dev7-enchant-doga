package jp.dev7.enchant.doga.parser.l2c;

import java.io.File;

import jp.dev7.enchant.doga.parser.Props;
import jp.dev7.enchant.doga.parser.data.Connection;
import junit.framework.TestCase;

public class L2cFileParserTest extends TestCase {

    public void testParse() throws Exception {

        L2cFileParser parser = new L2cFileParser();
        Connection conn = parser.parse(new File(Props.dataDir(),
                "connect/l2_samp/demodata/walker_a.l2c"));

        assertNotNull(conn);
        assertNotNull(conn.getRootUnit());
    }

}
