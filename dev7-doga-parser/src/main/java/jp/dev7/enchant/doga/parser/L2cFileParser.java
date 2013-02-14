package jp.dev7.enchant.doga.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import jp.dev7.enchant.doga.parser.data.Connection;
import jp.dev7.enchant.doga.parser.data.ConnectionObj;
import jp.dev7.enchant.doga.parser.l2c.autogen.ASTFileName;
import jp.dev7.enchant.doga.parser.l2c.autogen.ASTFunc;
import jp.dev7.enchant.doga.parser.l2c.autogen.ASTMov;
import jp.dev7.enchant.doga.parser.l2c.autogen.ASTNum;
import jp.dev7.enchant.doga.parser.l2c.autogen.ASTObj;
import jp.dev7.enchant.doga.parser.l2c.autogen.ASTRotx;
import jp.dev7.enchant.doga.parser.l2c.autogen.ASTRoty;
import jp.dev7.enchant.doga.parser.l2c.autogen.ASTRotz;
import jp.dev7.enchant.doga.parser.l2c.autogen.ASTScal;
import jp.dev7.enchant.doga.parser.l2c.autogen.ASTStart;
import jp.dev7.enchant.doga.parser.l2c.autogen.ASTText;
import jp.dev7.enchant.doga.parser.l2c.autogen.ASTUnit;
import jp.dev7.enchant.doga.parser.l2c.autogen.ASTUnitMov;
import jp.dev7.enchant.doga.parser.l2c.autogen.L2cParser;
import jp.dev7.enchant.doga.parser.l2c.autogen.L2cParserVisitor;
import jp.dev7.enchant.doga.parser.l2c.autogen.SimpleNode;

public class L2cFileParser {

    public Connection parse(File file) throws Exception {
        final Connection result = new Connection();

        final InputStreamReader in = new InputStreamReader(new FileInputStream(
                file), "Shift_JIS");

        final L2cParser parser = new L2cParser(in);
        final ASTStart start = parser.Start();

        final L2cParserVisitor visitor = new L2cParserVisitor() {

            @Override
            public Object visit(ASTStart node, Object data) {
                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTUnit node, Object data) {
                final ConnectionObj unit = new ConnectionObj();
                node.childrenAccept(this, unit);

                if (data instanceof Connection) {
                    final Connection parent = (Connection) data;
                    parent.setRootUnit(unit);
                } else if (data instanceof ConnectionObj) {
                    final ConnectionObj parent = (ConnectionObj) data;
                    parent.getChildUnits().add(unit);
                }

                return data;
            }

            @Override
            public Object visit(ASTObj node, Object data) {
                if (data instanceof ConnectionObj) {
                    ConnectionObj unit = (ConnectionObj) data;
                    unit.setName(node.nodeValue);
                }
                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTFileName node, Object data) {
                if (data instanceof ConnectionObj) {
                    ConnectionObj unit = (ConnectionObj) data;
                    unit.setUnitFileName(node.nodeValue);
                }
                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTMov node, Object data) {
                if (data instanceof ConnectionObj) {
                    ConnectionObj unit = (ConnectionObj) data;
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
                if (data instanceof ConnectionObj) {
                    ConnectionObj unit = (ConnectionObj) data;
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
                if (data instanceof ConnectionObj) {
                    ConnectionObj unit = (ConnectionObj) data;
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
                if (data instanceof ConnectionObj) {
                    ConnectionObj unit = (ConnectionObj) data;
                    Double value = (Double) node.jjtGetChild(0).jjtAccept(this,
                            null);
                    unit.setRotx(value);
                }
                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTRoty node, Object data) {
                if (data instanceof ConnectionObj) {
                    ConnectionObj unit = (ConnectionObj) data;
                    Double value = (Double) node.jjtGetChild(0).jjtAccept(this,
                            null);
                    unit.setRoty(value);
                }
                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTRotz node, Object data) {
                if (data instanceof ConnectionObj) {
                    ConnectionObj unit = (ConnectionObj) data;
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
