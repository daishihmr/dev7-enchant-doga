/* Generated By:JJTree: Do not edit this line. ASTV.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.parser.suf.autogen;

public
class ASTV extends SimpleNode {
  public ASTV(int id) {
    super(id);
  }

  public ASTV(SufParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SufParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=0457ac8c93bfad7df44e028dc2339107 (do not edit this line) */
