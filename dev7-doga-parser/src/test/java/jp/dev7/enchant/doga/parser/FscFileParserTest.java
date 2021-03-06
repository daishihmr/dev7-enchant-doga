package jp.dev7.enchant.doga.parser;

import java.io.File;
import java.util.List;

import jp.dev7.enchant.doga.parser.data.Unit;
import jp.dev7.enchant.doga.parser.util.FileTreeUtil;
import jp.dev7.enchant.doga.parser.util.Props;
import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

import com.google.common.base.Function;

public class FscFileParserTest extends TestCase {

    public void test1() throws Exception {
        final Logger logger = LoggerFactory.getLogger("jp.dev7");
        ((ch.qos.logback.classic.Logger) logger).setLevel(Level.WARN);

        final FscFileParser parser = new FscFileParser();
        List<Unit> results = FileTreeUtil.scanDir(new File(Props.dataDir(),
                "mecha/l1_samp"), new Function<File, Unit>() {
            @Override
            public Unit apply(File input) {
                if (input.getName().toLowerCase().endsWith(".fsc")) {
                    try {
                        return parser.parse(input);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    return null;
                }
            }
        });
        assertFalse(results.isEmpty());
        for (Unit fsc : results) {
            System.out.println(fsc);
        }
    }

}
