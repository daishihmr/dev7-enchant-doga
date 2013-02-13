package jp.dev7.enchant.doga.parser.suf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import jp.dev7.enchant.doga.parser.atr.AtrFileParser;
import jp.dev7.enchant.doga.parser.atr.data.Atr;
import jp.dev7.enchant.doga.parser.suf.autogen.ASTAttrObj;
import jp.dev7.enchant.doga.parser.suf.autogen.ASTAttribute;
import jp.dev7.enchant.doga.parser.suf.autogen.ASTNormalX;
import jp.dev7.enchant.doga.parser.suf.autogen.ASTNormalY;
import jp.dev7.enchant.doga.parser.suf.autogen.ASTNormalZ;
import jp.dev7.enchant.doga.parser.suf.autogen.ASTObj;
import jp.dev7.enchant.doga.parser.suf.autogen.ASTPolygonVertex;
import jp.dev7.enchant.doga.parser.suf.autogen.ASTPrimitive;
import jp.dev7.enchant.doga.parser.suf.autogen.ASTShadeVertex;
import jp.dev7.enchant.doga.parser.suf.autogen.ASTStart;
import jp.dev7.enchant.doga.parser.suf.autogen.ASTU;
import jp.dev7.enchant.doga.parser.suf.autogen.ASTUvPolyVertex;
import jp.dev7.enchant.doga.parser.suf.autogen.ASTUvShadeVertex;
import jp.dev7.enchant.doga.parser.suf.autogen.ASTV;
import jp.dev7.enchant.doga.parser.suf.autogen.ASTX;
import jp.dev7.enchant.doga.parser.suf.autogen.ASTY;
import jp.dev7.enchant.doga.parser.suf.autogen.ASTZ;
import jp.dev7.enchant.doga.parser.suf.autogen.ParseException;
import jp.dev7.enchant.doga.parser.suf.autogen.SimpleNode;
import jp.dev7.enchant.doga.parser.suf.autogen.SufParser;
import jp.dev7.enchant.doga.parser.suf.autogen.SufParserVisitor;
import jp.dev7.enchant.doga.parser.suf.data.Obj;
import jp.dev7.enchant.doga.parser.suf.data.Prim;
import jp.dev7.enchant.doga.parser.suf.data.Suf;
import jp.dev7.enchant.doga.parser.suf.data.Vertex;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class SufFileParser {
    public Suf parseSufAtr(File file) throws Exception {
        // atrファイル
        final File atrFile;
        {
            String absolutePath = file.getAbsolutePath();
            int extIndex = absolutePath.lastIndexOf(".suf");
            if (extIndex < 0) {
                extIndex = absolutePath.lastIndexOf(".SUF");
            }

            if (extIndex < 0) {
                atrFile = null;
            } else {
                atrFile = new File(absolutePath.substring(0, extIndex) + ".atr");
            }
        }

        final Suf result = parse(file);

        if (atrFile.exists()) {
            List<Atr> atr = AtrFileParser.parse(atrFile);
            result.getAtrMap().putAll(
                    Maps.uniqueIndex(atr, new Function<Atr, String>() {
                        @Override
                        public String apply(Atr input) {
                            return input.getName();
                        }

                    }));
        }

        return result;
    }

    public Suf parse(File file) throws FileNotFoundException, ParseException,
            UnsupportedEncodingException {
        final Reader in = new InputStreamReader(new FileInputStream(file),
                "Shift_JIS");
        try {

            final SufParser sufParser = new SufParser(in);
            final ASTStart start = sufParser.Start();

            final Suf result = new Suf();
            final SufParserVisitor visitor = new SufParserVisitor() {

                @Override
                public Object visit(ASTStart node, Object data) {
                    for (int i = 0; i < node.jjtGetNumChildren(); i++) {
                        Obj obj = (Obj) node.jjtGetChild(i).jjtAccept(this,
                                null);
                        result.getObjects().add(obj);
                    }
                    return result;
                }

                @Override
                public Object visit(ASTObj node, Object data) {
                    Obj obj = new Obj();
                    obj.setName(node.nodeValue);
                    node.childrenAccept(this, obj);
                    return obj;
                }

                @Override
                public Object visit(ASTAttrObj node, Object data) {
                    Obj obj = (Obj) data;

                    String lastAtr = null;
                    for (int i = 0; i < node.jjtGetNumChildren(); i++) {
                        Object result = node.jjtGetChild(i).jjtAccept(this,
                                data);
                        if (result == null) {
                            continue;
                        } else if (result instanceof String) {

                            lastAtr = (String) result;

                        } else if (result instanceof Prim) {

                            Prim prim = (Prim) result;
                            prim.setAtrName(lastAtr);

                            obj.getPrimitives().add(prim);
                        }
                    }

                    return obj;
                }

                @Override
                public Object visit(ASTAttribute node, Object data) {
                    return node.nodeValue;
                }

                @Override
                public Object visit(ASTPrimitive node, Object data) {
                    Prim prim = new Prim();
                    for (int i = 0; i < node.jjtGetNumChildren(); i++) {
                        node.jjtGetChild(i).jjtAccept(this, prim);
                    }
                    return prim;
                }

                @Override
                public Object visit(ASTPolygonVertex node, Object data) {
                    return vertex(node, data);
                }

                @Override
                public Object visit(ASTShadeVertex node, Object data) {
                    return vertex(node, data);
                }

                @Override
                public Object visit(ASTUvPolyVertex node, Object data) {
                    return vertex(node, data);
                }

                @Override
                public Object visit(ASTUvShadeVertex node, Object data) {
                    return vertex(node, data);
                }

                private Object vertex(SimpleNode node, Object data) {
                    Prim prim = (Prim) data;
                    Vertex v = (Vertex) node.childrenAccept(this, new Vertex());
                    prim.getVertices().add(v);
                    return prim;
                }

                @Override
                public Object visit(ASTV node, Object data) {
                    ((Vertex) data).setV(Double.parseDouble(node.nodeValue));
                    return data;
                }

                @Override
                public Object visit(ASTU node, Object data) {
                    ((Vertex) data).setU(Double.parseDouble(node.nodeValue));
                    return data;
                }

                @Override
                public Object visit(ASTNormalZ node, Object data) {
                    ((Vertex) data).setNormalZ(Double
                            .parseDouble(node.nodeValue));
                    return data;
                }

                @Override
                public Object visit(ASTNormalY node, Object data) {
                    ((Vertex) data).setNormalY(Double
                            .parseDouble(node.nodeValue));
                    return data;
                }

                @Override
                public Object visit(ASTNormalX node, Object data) {
                    ((Vertex) data).setNormalX(Double
                            .parseDouble(node.nodeValue));
                    return data;
                }

                @Override
                public Object visit(ASTZ node, Object data) {
                    ((Vertex) data).setZ(Double.parseDouble(node.nodeValue));
                    return data;
                }

                @Override
                public Object visit(ASTY node, Object data) {
                    ((Vertex) data).setY(Double.parseDouble(node.nodeValue));
                    return data;
                }

                @Override
                public Object visit(ASTX node, Object data) {
                    ((Vertex) data).setX(Double.parseDouble(node.nodeValue));
                    return data;
                }

                @Override
                public Object visit(SimpleNode node, Object data) {
                    return null;
                }

            };

            start.jjtAccept(visitor, null);

            return result;

        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
        }

    }
}
