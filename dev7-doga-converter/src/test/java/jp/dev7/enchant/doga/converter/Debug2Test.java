package jp.dev7.enchant.doga.converter;

import java.io.File;
import java.io.FileWriter;

import jp.dev7.enchant.doga.converter.UnitConverter;
import junit.framework.TestCase;

public class Debug2Test extends TestCase {

    public void test1() throws Exception {
        UnitConverter converter = new UnitConverter();
        converter.convertAndWriteJson(
                new File("src/test/resources/sakana.l3p"), new FileWriter(
                        "target/debug2.l3p.js"));
    }
}
