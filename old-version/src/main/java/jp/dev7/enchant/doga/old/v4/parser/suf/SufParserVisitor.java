/* Generated By:JavaCC: Do not edit this line. SufParserVisitor.java Version 5.0 */
package jp.dev7.enchant.doga.old.v4.parser.suf;

public interface SufParserVisitor
{
  public Object visit(SimpleNode node, Object data);
  public Object visit(ASTStart node, Object data);
  public Object visit(ASTObj node, Object data);
  public Object visit(ASTAttrObj node, Object data);
  public Object visit(ASTAttribute node, Object data);
  public Object visit(ASTPrimitive node, Object data);
  public Object visit(ASTPolygonVertex node, Object data);
  public Object visit(ASTShadeVertex node, Object data);
  public Object visit(ASTUvPolyVertex node, Object data);
  public Object visit(ASTUvShadeVertex node, Object data);
  public Object visit(ASTX node, Object data);
  public Object visit(ASTY node, Object data);
  public Object visit(ASTZ node, Object data);
  public Object visit(ASTNormalX node, Object data);
  public Object visit(ASTNormalY node, Object data);
  public Object visit(ASTNormalZ node, Object data);
  public Object visit(ASTU node, Object data);
  public Object visit(ASTV node, Object data);
}
/* JavaCC - OriginalChecksum=b9d2e067c5969aca563ba0ac9192e85c (do not edit this line) */