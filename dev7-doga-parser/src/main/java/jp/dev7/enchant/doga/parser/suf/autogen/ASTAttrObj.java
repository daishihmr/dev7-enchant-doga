/* Generated By:JJTree: Do not edit this line. ASTAttrObj.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.parser.suf.autogen;

public
class ASTAttrObj extends SimpleNode {
  public ASTAttrObj(int id) {
    super(id);
  }

  public ASTAttrObj(SufParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SufParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=5286bcd24a53968b58475c774a775f00 (do not edit this line) */
