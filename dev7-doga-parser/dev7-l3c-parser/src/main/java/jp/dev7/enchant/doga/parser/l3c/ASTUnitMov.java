/* Generated By:JJTree: Do not edit this line. ASTUnitMov.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.parser.l3c;

public
class ASTUnitMov extends SimpleNode {
  public ASTUnitMov(int id) {
    super(id);
  }

  public ASTUnitMov(L3cParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(L3cParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=5fd21cc7dc0599d3a043c9304b5469da (do not edit this line) */
