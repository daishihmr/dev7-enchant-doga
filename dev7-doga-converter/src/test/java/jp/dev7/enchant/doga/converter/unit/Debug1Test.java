package jp.dev7.enchant.doga.converter.unit;

import java.io.File;
import java.io.FileWriter;

import jp.dev7.enchant.doga.converter.UnitConverter;
import junit.framework.TestCase;

public class Debug1Test extends TestCase {

    public void test1() throws Exception {
        UnitConverter converter = new UnitConverter();
        converter.convertAndWriteJson(new File("src/test/resources/tori.L3P"),
                new FileWriter("target/debug1.l3p.js"));
    }
}
