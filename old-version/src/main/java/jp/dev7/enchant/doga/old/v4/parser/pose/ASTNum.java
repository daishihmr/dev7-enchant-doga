/* Generated By:JJTree: Do not edit this line. ASTNum.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.old.v4.parser.pose;

public
class ASTNum extends SimpleNode {
  public ASTNum(int id) {
    super(id);
  }

  public ASTNum(PoseParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(PoseParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=496a964a7e9af4b596d62060ddf3dacf (do not edit this line) */
