/* Generated By:JJTree: Do not edit this line. ASTAtrName.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.parser.atr.autogen;

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
/* JavaCC - OriginalChecksum=ee5da3a0f6848f41c9d006df0f42d7ad (do not edit this line) */
