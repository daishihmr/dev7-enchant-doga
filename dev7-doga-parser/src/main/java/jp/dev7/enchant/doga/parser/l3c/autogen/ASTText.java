/* Generated By:JJTree: Do not edit this line. ASTText.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.parser.l3c.autogen;

public
class ASTText extends SimpleNode {
  public ASTText(int id) {
    super(id);
  }

  public ASTText(L3cParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(L3cParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=7cf64b87eeffea3163cf6dd99fb9a63c (do not edit this line) */
