package jp.dev7.enchant.doga.converter.l3c;

import java.io.File;
import java.io.PrintWriter;

import jp.dev7.enchant.doga.converter.EnchantArticulated;
import junit.framework.TestCase;
import net.arnx.jsonic.JSON;

public class L3cConverterTest extends TestCase {

	public void test43() throws Exception {
		L3cConverter conv = new L3cConverter();
		conv.convert(new File("src/test/resources/darkhero.l3c"));
	}

	public void testConvert() throws Exception {
		L3cConverter conv = new L3cConverter();
		EnchantArticulated result = conv.convert(new File(
				"src/test/resources/heavyms.l3c"));

		JSON.encode(result, new PrintWriter("target/result.js"), true);
	}

}
