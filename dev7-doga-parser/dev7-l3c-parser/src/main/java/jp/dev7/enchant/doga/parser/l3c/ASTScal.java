/* Generated By:JJTree: Do not edit this line. ASTScal.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.parser.l3c;

public
class ASTScal extends SimpleNode {
  public ASTScal(int id) {
    super(id);
  }

  public ASTScal(L3cParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(L3cParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=163750d5b4149f6d62d9d411ad37fc3c (do not edit this line) */
