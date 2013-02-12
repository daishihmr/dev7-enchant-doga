package jp.dev7.enchant.doga.parser.suf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import jp.dev7.enchant.doga.parser.Props;
import jp.dev7.enchant.doga.parser.suf.data.Obj;
import jp.dev7.enchant.doga.parser.suf.data.Prim;
import jp.dev7.enchant.doga.parser.suf.data.Suf;
import junit.framework.TestCase;

import com.google.common.collect.Sets;

public class SufFileParserTest extends TestCase {

	private Set<String> atrNameSet = Sets.newHashSet();

	public void testParse() throws Exception {
		SufFileParser.parse(new File("src/test/resources/heavyms.suf"));
		SufFileParser.parse(new File("src/test/resources/ms_kage.SUF"));

		File partsDir = new File(Props.commonDir(), "parts");
		parse(partsDir);
	}

	public void testParse2() throws Exception {
		Suf suf1 = SufFileParser.parseSufAtr(new File(
				"src/test/resources/heavyms.suf"));
		assertFalse(suf1.getObjects().isEmpty());
		assertFalse(suf1.getAtrMap().isEmpty());

		Suf suf2 = SufFileParser.parseSufAtr(new File(
				"src/test/resources/ms_kage.SUF"));
		assertFalse(suf2.getObjects().isEmpty());
		assertFalse(suf2.getAtrMap().isEmpty());

		Suf suf3 = SufFileParser.parseSufAtr(new File(
				"src/test/resources/noatr.suf"));
		assertFalse(suf3.getObjects().isEmpty());
		assertTrue(suf3.getAtrMap().isEmpty());
	}

	private void parse(File dir) throws FileNotFoundException, ParseException,
			UnsupportedEncodingException {
		for (File f : dir.listFiles()) {
			if (f.isFile() && f.getName().toLowerCase().endsWith(".suf")) {
				System.out.println(f);
				Suf suf = SufFileParser.parse(f);
				for (Obj obj : suf.getObjects()) {
					for (Prim prim : obj.getPrimitives()) {
						atrNameSet.add(prim.getAtrName());
					}
				}
			} else if (f.isDirectory()) {
				parse(f);
			}
		}
	}
}
