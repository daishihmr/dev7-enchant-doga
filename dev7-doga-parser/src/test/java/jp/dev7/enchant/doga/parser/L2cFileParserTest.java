package jp.dev7.enchant.doga.parser;

import java.io.File;
import java.util.List;

import jp.dev7.enchant.doga.parser.data.Connection;
import jp.dev7.enchant.doga.parser.data.ConnectionObj;
import jp.dev7.enchant.doga.parser.util.FileTreeUtil;
import jp.dev7.enchant.doga.parser.util.Props;
import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

import com.google.common.base.Function;

public class L2cFileParserTest extends TestCase {

    public void testParse() throws Exception {

        L2cFileParser parser = new L2cFileParser();
        Connection conn = parser.parse(new File(Props.dataDir(),
                "connect/l2_samp/demodata/walker_a.l2c"));

        assertNotNull(conn);
        assertNotNull(conn.getRootUnit());
    }

    public void test1() throws Exception {
        final Logger logger = LoggerFactory.getLogger("jp.dev7");
        ((ch.qos.logback.classic.Logger) logger).setLevel(Level.WARN);

        final L2cFileParser parser = new L2cFileParser();
        List<Connection> results = FileTreeUtil.scanDir(Props.dataDir(),
                new Function<File, Connection>() {
                    @Override
                    public Connection apply(File input) {
                        if (input.getName().toLowerCase().endsWith(".l2c")) {
                            try {
                                return parser.parse(input);
                            } catch (Exception e) {
                                logger.error(input.getAbsolutePath(), e);
                                return null;
                            }
                        } else {
                            return null;
                        }
                    }
                });
        assertFalse(results.isEmpty());
        for (Connection conn : results) {
            ConnectionObj u = conn.getRootUnit();
            scan(u, new Function<ConnectionObj, Void>() {
                @Override
                public Void apply(ConnectionObj input) {
                    String filename = input.getUnitFileName();
                    System.out.println(filename);

                    return null;
                }
            });

        }
    }

    private void scan(ConnectionObj unit, Function<ConnectionObj, Void> f) {
        f.apply(unit);
        for (ConnectionObj child : unit.getChildUnits()) {
            scan(child, f);
        }
    }

}
