/* Generated By:JJTree: Do not edit this line. ASTPrimitive.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.parser.suf.autogen;

public
class ASTPrimitive extends SimpleNode {
  public ASTPrimitive(int id) {
    super(id);
  }

  public ASTPrimitive(SufParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SufParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=ad280792a157f70474356185d96e0733 (do not edit this line) */
