/* Generated By:JJTree: Do not edit this line. ASTFunc.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.parser.atr.autogen;

public class ASTFunc extends SimpleNode {
    public ASTFunc(int id) {
        super(id);
    }

    public ASTFunc(AtrParser p, int id) {
        super(p, id);
    }

    /** Accept the visitor. **/
    public Object jjtAccept(AtrParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=6757a9b758f87b7cbc28fe90bce223fa (do not edit this line) */
