/* Generated By:JJTree: Do not edit this line. ASTAttrObj.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.parser.suf;

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
/* JavaCC - OriginalChecksum=0621f88c49af18c4d050559080f4f9f7 (do not edit this line) */
