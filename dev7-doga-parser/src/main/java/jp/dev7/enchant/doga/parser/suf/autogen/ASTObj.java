/* Generated By:JJTree: Do not edit this line. ASTObj.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.parser.suf.autogen;

public
class ASTObj extends SimpleNode {
  public ASTObj(int id) {
    super(id);
  }

  public ASTObj(SufParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SufParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=5572ac48f36a5c721c3c5671ef9fdbf0 (do not edit this line) */
