/* Generated By:JJTree: Do not edit this line. ASTFunc.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.old.v4.parser.l3p;

public
class ASTFunc extends SimpleNode {
  public ASTFunc(int id) {
    super(id);
  }

  public ASTFunc(L3pParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(L3pParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=79ffc27e56320a03ae68c1f2937ed4d8 (do not edit this line) */
