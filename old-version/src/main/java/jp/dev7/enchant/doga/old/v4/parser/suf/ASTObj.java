/* Generated By:JJTree: Do not edit this line. ASTObj.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=jp.dev7.enchant.doga.parser.BaseNode,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jp.dev7.enchant.doga.old.v4.parser.suf;

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
/* JavaCC - OriginalChecksum=dfee6f66e35dc9b4c31b9bfa4abc533d (do not edit this line) */
