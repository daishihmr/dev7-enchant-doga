package jp.dev7.enchant.doga.parser.l3c;

import java.io.StringReader;

import jp.dev7.enchant.doga.parser.l3c.data.Pose;
import jp.dev7.enchant.doga.parser.pose.autogen.ASTName;
import jp.dev7.enchant.doga.parser.pose.autogen.ASTNum;
import jp.dev7.enchant.doga.parser.pose.autogen.ASTPose;
import jp.dev7.enchant.doga.parser.pose.autogen.ASTStart;
import jp.dev7.enchant.doga.parser.pose.autogen.ASTUnitPose;
import jp.dev7.enchant.doga.parser.pose.autogen.PoseParser;
import jp.dev7.enchant.doga.parser.pose.autogen.PoseParserVisitor;
import jp.dev7.enchant.doga.parser.pose.autogen.SimpleNode;

public class PoseLineParser {

    private PoseLineParser() {
    }

    public static Pose parse(String line) throws Exception {
        final Pose result = new Pose();

        final PoseParser parser = new PoseParser(new StringReader(line));
        final ASTStart start = parser.Start();
        final PoseParserVisitor visitor = new PoseParserVisitor() {

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
            public Object visit(ASTUnitPose node, Object data) {
                Pose pose = (Pose) data;
                String label = (String) node.jjtGetChild(0).jjtAccept(this,
                        null);
                String movX = (String) node.jjtGetChild(1)
                        .jjtAccept(this, null);
                String movY = (String) node.jjtGetChild(2)
                        .jjtAccept(this, null);
                String movZ = (String) node.jjtGetChild(3)
                        .jjtAccept(this, null);
                String rotX = (String) node.jjtGetChild(4)
                        .jjtAccept(this, null);
                String rotY = (String) node.jjtGetChild(5)
                        .jjtAccept(this, null);
                String rotZ = (String) node.jjtGetChild(6)
                        .jjtAccept(this, null);

                pose.getLabels().add(label);
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
