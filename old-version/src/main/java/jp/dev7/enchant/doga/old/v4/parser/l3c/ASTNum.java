/* Generated By:JJTree: Do not edit this line. ASTNum.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.old.v4.parser.l3c;

public
class ASTNum extends SimpleNode {
  public ASTNum(int id) {
    super(id);
  }

  public ASTNum(L3cParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(L3cParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=e9c839f9af3e91e0e1d488883a6d0882 (do not edit this line) */