/* Generated By:JJTree: Do not edit this line. ASTStart.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.parser.atr.autogen;

public
class ASTStart extends SimpleNode {
  public ASTStart(int id) {
    super(id);
  }

  public ASTStart(AtrParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(AtrParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4d22096b1a1d1e17b5f328517a5dd23e (do not edit this line) */
