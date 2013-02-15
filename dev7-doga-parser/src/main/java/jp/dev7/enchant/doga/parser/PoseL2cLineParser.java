package jp.dev7.enchant.doga.parser;

import java.io.StringReader;

import jp.dev7.enchant.doga.parser.data.Pose;
import jp.dev7.enchant.doga.parser.posel2c.autogen.ASTName;
import jp.dev7.enchant.doga.parser.posel2c.autogen.ASTNum;
import jp.dev7.enchant.doga.parser.posel2c.autogen.ASTPose;
import jp.dev7.enchant.doga.parser.posel2c.autogen.ASTStart;
import jp.dev7.enchant.doga.parser.posel2c.autogen.ASTUnitMov;
import jp.dev7.enchant.doga.parser.posel2c.autogen.ASTUnitRot;
import jp.dev7.enchant.doga.parser.posel2c.autogen.PoseL2cParser;
import jp.dev7.enchant.doga.parser.posel2c.autogen.PoseL2cParserVisitor;
import jp.dev7.enchant.doga.parser.posel2c.autogen.SimpleNode;

public class PoseL2cLineParser {

    private PoseL2cLineParser() {
    }

    public static Pose parse(String line) throws Exception {
        final Pose result = new Pose();

        final PoseL2cParser parser = new PoseL2cParser(new StringReader(line));
        final ASTStart start = parser.Start();
        final PoseL2cParserVisitor visitor = new PoseL2cParserVisitor() {
            boolean isRootUnit = true;

            @Override
            public Object visit(ASTStart node, Object data) {
                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTPose node, Object data) {
                Pose pose = (Pose) data;
                String name = (String) node.jjtGetChild(0)
                        .jjtAccept(this, null);
                pose.setName(name);
                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTUnitMov node, Object data) {
                Pose pose = (Pose) data;
                String movX = (String) node.jjtGetChild(0)
                        .jjtAccept(this, null);
                String movY = (String) node.jjtGetChild(1)
                        .jjtAccept(this, null);
                String movZ = (String) node.jjtGetChild(2)
                        .jjtAccept(this, null);
                String rotX = "0";
                String rotY = "0";
                String rotZ = "0";

                pose.getUnitPose().add(
                        new double[] { Double.parseDouble(movX),
                                Double.parseDouble(movY),
                                Double.parseDouble(movZ),
                                Double.parseDouble(rotX),
                                Double.parseDouble(rotY),
                                Double.parseDouble(rotZ) });

                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTUnitRot node, Object data) {
                Pose pose = (Pose) data;
                String movX = "0";
                String movY = "0";
                String movZ = "0";
                String rotX = (String) node.jjtGetChild(0)
                        .jjtAccept(this, null);
                String rotY = (String) node.jjtGetChild(1)
                        .jjtAccept(this, null);
                String rotZ = (String) node.jjtGetChild(2)
                        .jjtAccept(this, null);

                if (isRootUnit) {
                    double[] base = pose.getUnitPose().get(0);
                    base[3] = Double.parseDouble(rotX);
                    base[4] = Double.parseDouble(rotY);
                    base[5] = Double.parseDouble(rotZ);
                } else {
                    pose.getUnitPose().add(
                            new double[] { Double.parseDouble(movX),
                                    Double.parseDouble(movY),
                                    Double.parseDouble(movZ),
                                    Double.parseDouble(rotX),
                                    Double.parseDouble(rotY),
                                    Double.parseDouble(rotZ) });
                }

                isRootUnit = false;

                return node.childrenAccept(this, data);
            }

            @Override
            public Object visit(ASTName node, Object data) {
                return node.nodeValue;
            }

            @Override
            public Object visit(ASTNum node, Object data) {
                return node.nodeValue;
            }

            @Override
            public Object visit(SimpleNode node, Object data) {
                return null;
            }

        };

        visitor.visit(start, result);

        return result;
    }
}
