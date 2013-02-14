package jp.dev7.enchant.doga.parser;

import java.io.File;
import java.util.List;

import jp.dev7.enchant.doga.parser.L2pFileParser;
import jp.dev7.enchant.doga.parser.data.Unit;
import jp.dev7.enchant.doga.parser.util.FileTreeUtil;
import jp.dev7.enchant.doga.parser.util.Props;
import junit.framework.TestCase;

import com.google.common.base.Function;

public class L2pFileParserTest extends TestCase {

    public void test1() throws Exception {
        final L2pFileParser parser = new L2pFileParser();
        List<Unit> results = FileTreeUtil.scanDir(new File(Props.dataDir(),
                "mecha/l2_samp"), new Function<File, Unit>() {
            @Override
            public Unit apply(File input) {
                if (input.getName().toLowerCase().endsWith(".l2p")) {
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