package jp.dev7.enchant.doga.parser.fsc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.dev7.enchant.doga.parser.fsc.autogen.ASTFram;
import jp.dev7.enchant.doga.parser.fsc.autogen.ASTFunc;
import jp.dev7.enchant.doga.parser.fsc.autogen.ASTFuncName;
import jp.dev7.enchant.doga.parser.fsc.autogen.ASTNum;
import jp.dev7.enchant.doga.parser.fsc.autogen.ASTObjData;
import jp.dev7.enchant.doga.parser.fsc.autogen.ASTStart;
import jp.dev7.enchant.doga.parser.fsc.autogen.FscParser;
import jp.dev7.enchant.doga.parser.fsc.autogen.FscParserVisitor;
import jp.dev7.enchant.doga.parser.fsc.autogen.ParseException;
import jp.dev7.enchant.doga.parser.fsc.autogen.SimpleNode;
import jp.dev7.enchant.doga.parser.fsc.data.Fsc;
import jp.dev7.enchant.doga.parser.fsc.data.FscObj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class FscFileParser {

    private static final Logger LOG = LoggerFactory
            .getLogger(FscFileParser.class);

    public Fsc parse(File fscFile) throws Exception {
        final Fsc fscData = new Fsc();

        // obj部分のコメント解析
        final Map<Integer, String> objSufMap = Maps.newHashMap();
        {
            final Pattern pattern = Pattern
                    .compile(".*obj \\\"?\\w+\\\"? /\\* \\\"?([^\"]+)\\\"? \\*/.*");
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fscFile),
                            "Shift_JIS"));
            String l;
            Integer i = 0;
            while ((l = reader.readLine()) != null) {
                Matcher m = pattern.matcher(l.trim());
                if (m.matches()) {
                    String sufFile;
                    sufFile = m.group(1);

                    objSufMap.put(i, sufFile);
                    i++;
                }
            }
            reader.close();
        }

        final InputStreamReader in = new InputStreamReader(new FileInputStream(
                fscFile), "Shift_JIS");
        try {
            final FscParser fscParser = new FscParser(in);
            ASTStart start;
            try {
                start = fscParser.Start();
            } catch (ParseException e) {
                LOG.error("FSCファイルパース中にエラー!! " + fscFile, e);
                throw e;
            }

            final FscParserVisitor visitor = new FscParserVisitor() {

                int i = 0;

                @Override
                public Object visit(ASTStart node, Object data) {
                    return node.childrenAccept(this, data);
                }

                @Override
                public Object visit(ASTFram node, Object data) {
                    return node.childrenAccept(this, data);
                }

                @Override
                public Object visit(ASTObjData node, Object data) {
                    Fsc fscData = (Fsc) data;

                    if (node.nodeValue != null) {
                        FscObj obj = new FscObj();
                        obj.setName(node.nodeValue);
                        obj.setSufFileName(objSufMap.get(i));
                        i++;
                        node.childrenAccept(this, obj);

                        fscData.getObjects().add(obj);
                    }
                    return null;
                }

                @Override
                public Object visit(ASTFunc node, Object data) {
                    if (data instanceof FscObj) {
                        FscObj obj = (FscObj) data;

                        _Func f = (_Func) node.jjtGetChild(0).jjtAccept(this,
                                null);
                        if (f == null) {
                            return null;
                        }

                        if (node.jjtGetNumChildren() > 0) {
                            for (int i = 1; i < node.jjtGetNumChildren(); i++) {
                                node.jjtGetChild(i).jjtAccept(this, f);
                            }
                        }

                        f.apply(obj);

                        return data;
                    } else {
                        return null;
                    }
                }

                @Override
                public Object visit(ASTFuncName node, Object data) {
                    if (node.nodeValue == null) {
                        return null;
                    } else if (node.nodeValue.equals("mov")) {
                        return new _Func.Mov();
                    } else if (node.nodeValue.equals("rotx")) {
                        return new _Func.RotX();
                    } else if (node.nodeValue.equals("roty")) {
                        return new _Func.RotY();
                    } else if (node.nodeValue.equals("rotz")) {
                        return new _Func.RotZ();
                    } else if (node.nodeValue.equals("scal")) {
                        return new _Func.Scal();
                    } else {
                        return null;
                    }
                }

                @Override
                public Object visit(ASTNum node, Object data) {
                    _Func f = (_Func) data;
                    if (f == null) {
                        return null;
                    } else {
                        f.addArg(Double.parseDouble(node.nodeValue));
                    }
                    return f;
                }

                @Override
                public Object visit(SimpleNode node, Object data) {
                    return null;
                }
            };

            start.jjtAccept(visitor, fscData);

            return fscData;

        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
    }

}
