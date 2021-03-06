/* Generated By:JJTree&JavaCC: Do not edit this line. PoseParserConstants.java */
package jp.dev7.enchant.doga.old.v4.parser.pose;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface PoseParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int COMMENT = 6;
  /** RegularExpression Id. */
  int LABELLED = 8;
  /** RegularExpression Id. */
  int MOVE = 9;
  /** RegularExpression Id. */
  int ROT = 10;
  /** RegularExpression Id. */
  int SELECTIVE = 11;
  /** RegularExpression Id. */
  int NORMAL = 12;
  /** RegularExpression Id. */
  int CM = 13;
  /** RegularExpression Id. */
  int DQ = 14;
  /** RegularExpression Id. */
  int DATA_START = 15;
  /** RegularExpression Id. */
  int DATA_END = 16;
  /** RegularExpression Id. */
  int NUM = 17;
  /** RegularExpression Id. */
  int NAME = 18;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int COMMENT_TEXT = 1;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\r\"",
    "\"\\t\"",
    "\"\\n\"",
    "\"/*\"",
    "\"*/\"",
    "<token of kind 7>",
    "\"labelled\"",
    "\"move\"",
    "\"rot\"",
    "\"selective\"",
    "\"normal\"",
    "\":\"",
    "\"\\\"\"",
    "\"(\"",
    "\")\"",
    "<NUM>",
    "<NAME>",
  };

}
