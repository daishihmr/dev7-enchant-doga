package jp.dev7.enchant.doga.parser.fsc;

import java.io.File;
import java.util.List;

import jp.dev7.enchant.doga.parser.fsc.data.Fsc;
import junit.framework.TestCase;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class FscFileParserTest extends TestCase {

    public void test1() throws Exception {
        final FscFileParser parser = new FscFileParser();
        List<Fsc> results = scanDir(new File("./src/test/resources/l1_samp"),
                new Function<File, Fsc>() {
                    @Override
                    public Fsc apply(File input) {
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
        for (Fsc fsc : results) {
            System.out.println(fsc);
        }
    }

    private <T> List<T> scanDir(File file, Function<File, T> f) {
        final List<T> result = Lists.newArrayList();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                result.addAll(scanDir(child, f));
            }
        } else {
            T t = f.apply(file);
            if (t != null) {
                result.add(t);
            }
        }
        return result;
    }

}
