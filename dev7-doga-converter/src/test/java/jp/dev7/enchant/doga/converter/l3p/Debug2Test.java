package jp.dev7.enchant.doga.converter.l3p;

import java.io.File;
import java.io.FileWriter;

import jp.dev7.enchant.doga.converter.l3p.L3pConverter;
import junit.framework.TestCase;

public class Debug2Test extends TestCase {

    public void test1() throws Exception {
        L3pConverter converter = new L3pConverter();
        converter.convertAndWriteJson(
                new File("src/test/resources/sakana.l3p"), new FileWriter(
                        "target/debug2.l3p.js"));
    }
}
