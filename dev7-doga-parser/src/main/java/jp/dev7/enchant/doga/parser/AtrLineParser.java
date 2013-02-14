package jp.dev7.enchant.doga.parser;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.dev7.enchant.doga.parser.atr.autogen.ParseException;
import jp.dev7.enchant.doga.parser.data.Atr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AtrLineParser {
    private static final Logger log = LoggerFactory
            .getLogger(AtrLineParser.class);

    private AtrLineParser() {
    }

    public static Atr parse(String line) throws IOException, ParseException {
        final String atrDef = line.substring(line.indexOf(":") + 1).trim();
        List<Atr> result;
        try {
            result = AtrFileParser.parseLine(atrDef);
        } catch (ParseException e) {
            log.error("atr parse error", e);
            if (log.isDebugEnabled()) {
                log.debug(line);
                String msg = e.getMessage();
                Pattern pat = Pattern.compile(".+ column ([0-9]+).+");
                Matcher m = pat.matcher(msg);
                if (m.find()) {
                    String column = m.group(1);
                    int c = Integer.parseInt(column);
                    StringBuffer cur = new StringBuffer();
                    for (int i = 0; i < c; i++) {
                        cur.append(" ");
                    }
                    log.debug(cur.toString() + "^");
                }
            }
            throw e;
        }
        if (result.isEmpty()) {
            throw new IllegalArgumentException("no data");
        }
        return result.get(0);
    }

}
