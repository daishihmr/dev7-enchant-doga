/* Generated By:JJTree: Do not edit this line. ASTNum.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.parser.l3p.autogen;

public
class ASTNum extends SimpleNode {
  public ASTNum(int id) {
    super(id);
  }

  public ASTNum(L3pParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(L3pParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=cf811539c204d1b921ab07cf40b174ef (do not edit this line) */
