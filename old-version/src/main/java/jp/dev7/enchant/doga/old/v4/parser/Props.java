package jp.dev7.enchant.doga.old.v4.parser;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Props {

	private static final String KEY_DOGA_COMMON_DIR = "dev7.doga.parser.dogaCommonDir";
	private static final String KEY_DOGA_DATA_DIR = "dev7.doga.parser.dogaDataDir";

	private Props() {
	}

	public static File commonDir() throws IOException {
		if (System.getProperty(KEY_DOGA_COMMON_DIR) != null) {
			return new File(System.getProperty(KEY_DOGA_COMMON_DIR));
		} else {
			return new File(load().getProperty(KEY_DOGA_COMMON_DIR));
		}
	}

	public static File dataDir() throws IOException {
		if (System.getProperty(KEY_DOGA_DATA_DIR) != null) {
			return new File(System.getProperty(KEY_DOGA_DATA_DIR));
		} else {
			return new File(load().getProperty(KEY_DOGA_DATA_DIR));
		}
	}

	private static Properties load() throws IOException {
		Properties prop = new Properties();
		prop.load(Props.class
				.getResourceAsStream("/dev7-doga-parser.properties"));
		return prop;
	}
}
