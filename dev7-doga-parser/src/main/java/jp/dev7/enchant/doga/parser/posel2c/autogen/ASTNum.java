/* Generated By:JJTree: Do not edit this line. ASTNum.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.parser.posel2c.autogen;

public
class ASTNum extends SimpleNode {
  public ASTNum(int id) {
    super(id);
  }

  public ASTNum(PoseL2cParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(PoseL2cParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=0d6353adeed3e1e20d2db06e90b3b1c2 (do not edit this line) */