/* Generated By:JJTree: Do not edit this line. ASTObjData.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.parser.fsc.autogen;

public
class ASTObjData extends SimpleNode {
  public ASTObjData(int id) {
    super(id);
  }

  public ASTObjData(FscParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(FscParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=a0b718a3392ae1a7334915737e2f8223 (do not edit this line) */
