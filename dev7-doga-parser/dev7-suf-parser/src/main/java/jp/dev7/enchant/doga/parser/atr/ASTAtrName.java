/* Generated By:JJTree: Do not edit this line. ASTAtrName.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.parser.atr;

public
class ASTAtrName extends SimpleNode {
  public ASTAtrName(int id) {
    super(id);
  }

  public ASTAtrName(AtrParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(AtrParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=6c9e1e14eb981021a7b403444b16209c (do not edit this line) */
