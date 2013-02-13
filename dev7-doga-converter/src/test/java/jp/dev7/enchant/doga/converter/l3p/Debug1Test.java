package jp.dev7.enchant.doga.converter.l3p;

import java.io.File;
import java.io.FileWriter;

import jp.dev7.enchant.doga.converter.l3p.L3pConverter;
import junit.framework.TestCase;

public class Debug1Test extends TestCase {

    public void test1() throws Exception {
        L3pConverter converter = new L3pConverter();
        converter.convertAndWriteJson(new File("src/test/resources/tori.L3P"),
                new FileWriter("target/debug1.l3p.js"));
    }
}
