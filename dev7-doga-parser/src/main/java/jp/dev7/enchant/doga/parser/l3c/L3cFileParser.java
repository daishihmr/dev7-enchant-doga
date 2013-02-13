package jp.dev7.enchant.doga.parser.l3c;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import jp.dev7.enchant.doga.parser.l3c.autogen.ASTFileName;
import jp.dev7.enchant.doga.parser.l3c.autogen.ASTFunc;
import jp.dev7.enchant.doga.parser.l3c.autogen.ASTMov;
import jp.dev7.enchant.doga.parser.l3c.autogen.ASTNum;
import jp.dev7.enchant.doga.parser.l3c.autogen.ASTObj;
import jp.dev7.enchant.doga.parser.l3c.autogen.ASTRotx;
import jp.dev7.enchant.doga.parser.l3c.autogen.ASTRoty;
import jp.dev7.enchant.doga.parser.l3c.autogen.ASTRotz;
import jp.dev7.enchant.doga.parser.l3c.autogen.ASTScal;
import jp.dev7.enchant.doga.parser.l3c.autogen.ASTStart;
import jp.dev7.enchant.doga.parser.l3c.autogen.ASTText;
import jp.dev7.enchant.doga.parser.l3c.autogen.ASTUnit;
import jp.dev7.enchant.doga.parser.l3c.autogen.ASTUnitMov;
import jp.dev7.enchant.doga.parser.l3c.autogen.L3cParser;
import jp.dev7.enchant.doga.parser.l3c.autogen.L3cParserVisitor;
import jp.dev7.enchant.doga.parser.l3c.autogen.SimpleNode;
import jp.dev7.enchant.doga.parser.l3c.data.L3c;
import jp.dev7.enchant.doga.parser.l3c.data.L3cObj;

public class L3cFileParser {

    public L3c parse(File file) throws Exception {
        final L3c result = new L3c();

        final InputStreamReader in = new InputStreamReader(new FileInputStream(
                file), "Shift_JIS");

        final L3cParser parser = new L3cParser(in);
        final ASTStart start = parser.Start();

        final L3cParserVisitor visitor = new L3cParserVisitor() {

            @Override
            public Object visit(ASTStart node, Object data) {
                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTUnit node, Object data) {
                final L3cObj unit = new L3cObj();
                node.childrenAccept(this, unit);

                if (data instanceof L3c) {
                    final L3c parent = (L3c) data;
                    parent.setRootUnit(unit);
                } else if (data instanceof L3cObj) {
                    final L3cObj parent = (L3cObj) data;
                    parent.getChildUnits().add(unit);
                }

                return data;
            }

            @Override
            public Object visit(ASTObj node, Object data) {
                if (data instanceof L3cObj) {
                    L3cObj unit = (L3cObj) data;
                    unit.setName(node.nodeValue);
                }
                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTFileName node, Object data) {
                if (data instanceof L3cObj) {
                    L3cObj unit = (L3cObj) data;
                    unit.setL3pFileName(node.nodeValue);
                }
                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTMov node, Object data) {
                if (data instanceof L3cObj) {
                    L3cObj unit = (L3cObj) data;
                    Double x = (Double) node.jjtGetChild(0).jjtAccept(this,
                            null);
                    Double y = (Double) node.jjtGetChild(1).jjtAccept(this,
                            null);
                    Double z = (Double) node.jjtGetChild(2).jjtAccept(this,
                            null);
                    unit.setMov(new double[] { x, y, z });

                    if (3 < node.jjtGetNumChildren()) {
                        Double p = (Double) node.jjtGetChild(4).jjtAccept(this,
                                null);
                        unit.setPosePointer(p.intValue());
                    }
                }
                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTUnitMov node, Object data) {
                if (data instanceof L3cObj) {
                    L3cObj unit = (L3cObj) data;
                    Double x = (Double) node.jjtGetChild(0).jjtAccept(this,
                            null);
                    Double y = (Double) node.jjtGetChild(1).jjtAccept(this,
                            null);
                    Double z = (Double) node.jjtGetChild(2).jjtAccept(this,
                            null);
                    unit.setUnitMov(new double[] { x, y, z });
                }
                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTScal node, Object data) {
                if (data instanceof L3cObj) {
                    L3cObj unit = (L3cObj) data;
                    Double x = (Double) node.jjtGetChild(0).jjtAccept(this,
                            null);
                    Double y = (Double) node.jjtGetChild(1).jjtAccept(this,
                            null);
                    Double z = (Double) node.jjtGetChild(2).jjtAccept(this,
                            null);
                    unit.setUnitScal(new double[] { x, y, z });
                }
                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTRotx node, Object data) {
                if (data instanceof L3cObj) {
                    L3cObj unit = (L3cObj) data;
                    Double value = (Double) node.jjtGetChild(0).jjtAccept(this,
                            null);
                    unit.setRotx(value);
                }
                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTRoty node, Object data) {
                if (data instanceof L3cObj) {
                    L3cObj unit = (L3cObj) data;
                    Double value = (Double) node.jjtGetChild(0).jjtAccept(this,
                            null);
                    unit.setRoty(value);
                }
                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTRotz node, Object data) {
                if (data instanceof L3cObj) {
                    L3cObj unit = (L3cObj) data;
                    Double value = (Double) node.jjtGetChild(0).jjtAccept(this,
                            null);
                    unit.setRotz(value);
                }
                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTText node, Object data) {
                return node.nodeValue;
            }

            @Override
            public Object visit(ASTNum node, Object data) {
                return Double.parseDouble(node.nodeValue);
            }

            @Override
            public Object visit(ASTFunc node, Object data) {
                return null;
            }

            @Override
            public Object visit(SimpleNode node, Object data) {
                return null;
            }
        };

        start.jjtAccept(visitor, result);

        return result;
    }
}
