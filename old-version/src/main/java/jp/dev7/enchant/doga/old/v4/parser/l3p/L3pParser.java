/* Generated By:JJTree&JavaCC: Do not edit this line. L3pParser.java */
package jp.dev7.enchant.doga.old.v4.parser.l3p;
public class L3pParser/*@bgen(jjtree)*/implements L3pParserTreeConstants, L3pParserConstants {/*@bgen(jjtree)*/
  protected JJTL3pParserState jjtree = new JJTL3pParserState();

  final public ASTStart Start() throws ParseException {
 /*@bgen(jjtree) Start */
  ASTStart jjtn000 = new ASTStart(JJTSTART);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      Fram();
                 jjtree.closeNodeScope(jjtn000, true);
                 jjtc000 = false;
                 {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  final public void Fram() throws ParseException {
 /*@bgen(jjtree) Fram */
  ASTFram jjtn000 = new ASTFram(JJTFRAM);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(FRAM);
      jj_consume_token(BLOCK_START);
      Light();
      label_1:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case BLOCK_START:
          ;
          break;
        default:
          jj_la1[0] = jj_gen;
          break label_1;
        }
        ObjData();
      }
      jj_consume_token(BLOCK_END);
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void Light() throws ParseException {
    jj_consume_token(LIGHT);
    Func();
  }

  final public void ObjData() throws ParseException {
 /*@bgen(jjtree) ObjData */
        ASTObjData jjtn000 = new ASTObjData(JJTOBJDATA);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);String obj;
    try {
      jj_consume_token(BLOCK_START);
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NAME:
          ;
          break;
        default:
          jj_la1[1] = jj_gen;
          break label_2;
        }
        Func();
      }
      obj = Obj();
      jj_consume_token(BLOCK_END);
          jjtree.closeNodeScope(jjtn000, true);
          jjtc000 = false;
                jjtn000.nodeValue = obj;
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public String Obj() throws ParseException {
        Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OBJ:
      jj_consume_token(OBJ);
      t = ObjName();
                              {if (true) return t.image;}
      break;
    case EYE:
      Eye();
                        {if (true) return null;}
      break;
    case TARGET:
      jj_consume_token(TARGET);
                           {if (true) return null;}
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Token ObjName() throws ParseException {
    Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OBJ:
      t = jj_consume_token(OBJ);
      break;
    case DQ:
      jj_consume_token(DQ);
      t = jj_consume_token(NUMNAME);
      jj_consume_token(DQ);
      break;
    case NAME:
      t = jj_consume_token(NAME);
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
        {if (true) return t;}
    throw new Error("Missing return statement in function");
  }

  final public void Eye() throws ParseException {
    jj_consume_token(EYE);
    Func();
  }

  final public void Func() throws ParseException {
 /*@bgen(jjtree) Func */
  ASTFunc jjtn000 = new ASTFunc(JJTFUNC);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      FuncName();
      jj_consume_token(DATA_START);
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUM:
          Num();
          break;
        case NAME:
          Func();
          break;
        default:
          jj_la1[4] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NUM:
        case NAME:
          ;
          break;
        default:
          jj_la1[5] = jj_gen;
          break label_3;
        }
      }
      jj_consume_token(DATA_END);
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void FuncName() throws ParseException {
 /*@bgen(jjtree) FuncName */
        ASTFuncName jjtn000 = new ASTFuncName(JJTFUNCNAME);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(NAME);
          jjtree.closeNodeScope(jjtn000, true);
          jjtc000 = false;
                jjtn000.nodeValue = t.image;
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void Num() throws ParseException {
 /*@bgen(jjtree) Num */
        ASTNum jjtn000 = new ASTNum(JJTNUM);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(NUM);
          jjtree.closeNodeScope(jjtn000, true);
          jjtc000 = false;
                jjtn000.nodeValue = t.image;
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  /** Generated Token Manager. */
  public L3pParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[6];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x10000,0x4000,0xe00,0x104200,0x6000,0x6000,};
   }

  /** Constructor with InputStream. */
  public L3pParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public L3pParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new L3pParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public L3pParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new L3pParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public L3pParser(L3pParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(L3pParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[21];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 6; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 21; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
