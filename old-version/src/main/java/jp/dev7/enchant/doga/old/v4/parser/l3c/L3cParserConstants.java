/* Generated By:JJTree&JavaCC: Do not edit this line. L3cParserConstants.java */
package jp.dev7.enchant.doga.old.v4.parser.l3c;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface L3cParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int COMMENT = 6;
  /** RegularExpression Id. */
  int FRAM = 8;
  /** RegularExpression Id. */
  int MOV = 9;
  /** RegularExpression Id. */
  int ROTZ = 10;
  /** RegularExpression Id. */
  int ROTY = 11;
  /** RegularExpression Id. */
  int ROTX = 12;
  /** RegularExpression Id. */
  int POSE = 13;
  /** RegularExpression Id. */
  int P = 14;
  /** RegularExpression Id. */
  int SCAL = 15;
  /** RegularExpression Id. */
  int OBJ = 16;
  /** RegularExpression Id. */
  int LIGHT = 17;
  /** RegularExpression Id. */
  int EYE = 18;
  /** RegularExpression Id. */
  int TARGET = 19;
  /** RegularExpression Id. */
  int SELECTIVE = 20;
  /** RegularExpression Id. */
  int DONE = 21;
  /** RegularExpression Id. */
  int SELECTIVEIF = 22;
  /** RegularExpression Id. */
  int SELECTIVEENDIF = 23;
  /** RegularExpression Id. */
  int FUNCNAME = 24;
  /** RegularExpression Id. */
  int PLUS = 25;
  /** RegularExpression Id. */
  int BLOCK_START = 26;
  /** RegularExpression Id. */
  int BLOCK_END = 27;
  /** RegularExpression Id. */
  int PARAM_START = 28;
  /** RegularExpression Id. */
  int PARAM_END = 29;
  /** RegularExpression Id. */
  int DATA_START = 30;
  /** RegularExpression Id. */
  int DATA_END = 31;
  /** RegularExpression Id. */
  int INDEX_START = 32;
  /** RegularExpression Id. */
  int INDEX_END = 33;
  /** RegularExpression Id. */
  int DQ = 34;
  /** RegularExpression Id. */
  int YEN = 35;
  /** RegularExpression Id. */
  int COM = 36;
  /** RegularExpression Id. */
  int EQUAL = 37;
  /** RegularExpression Id. */
  int FUNC_START = 38;
  /** RegularExpression Id. */
  int CONNECTION_FUNCNAME = 39;
  /** RegularExpression Id. */
  int FUNC_END = 40;
  /** RegularExpression Id. */
  int NUM = 41;
  /** RegularExpression Id. */
  int NAME = 42;
  /** RegularExpression Id. */
  int NUMNAME = 43;

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
    "\"fram\"",
    "\"mov\"",
    "\"rotz\"",
    "\"roty\"",
    "\"rotx\"",
    "\"pose\"",
    "\"p\"",
    "\"scal\"",
    "\"obj\"",
    "\"light\"",
    "\"eye\"",
    "\"target\"",
    "\"Selective\"",
    "\"Done\"",
    "\"#if\"",
    "\"#endif\"",
    "<FUNCNAME>",
    "\"+\"",
    "\"{\"",
    "\"}\"",
    "\"(:\"",
    "\":)\"",
    "\"(\"",
    "\")\"",
    "\"[\"",
    "\"]\"",
    "\"\\\"\"",
    "\"\\\\\"",
    "\",\"",
    "\"==\"",
    "\"#func\"",
    "\"CONNECTION_FUNCNAME\"",
    "\"#endfunc\"",
    "<NUM>",
    "<NAME>",
    "<NUMNAME>",
  };

}
