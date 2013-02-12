package jp.dev7.enchant.doga.parser.atr;

import jp.dev7.enchant.doga.parser.atr.AtrLineParser;
import jp.dev7.enchant.doga.parser.atr.data.Atr;
import junit.framework.TestCase;

public class AtrLineParserTest extends TestCase {

    public void test42() throws Exception {
        String a = "	0: atr \"#01 あお\" { col ( rgb ( 1.000 0.150 0.750 ) ) amb ( 0.200 ) dif ( 0.800 ) spc ( 0.700 1.000 0.000 ) }";
        Atr atr = AtrLineParser.parse(a);
        assertEquals(atr.getName(), "#01 あお");

        AtrLineParser
                .parse("	1: atr \"#02\" { col ( rgb ( 0.600 0.900 1.000 ) ) amb ( 0.200 ) dif ( 0.800 ) tra ( 0.630 ) spc ( 0.700 1.000 0.000 ) }");
        AtrLineParser
                .parse("	2: atr noname { col ( rgb ( 0.700 1.000 0.050 ) ) amb ( 0.200 ) dif ( 0.800 ) spc ( 0.700 1.000 0.000 ) }");
    }

    public void testParse1() throws Exception {
        Atr atr = AtrLineParser.parse("0: atr \"#01\" {" //
                + " col ( rgb ( 0.600 0.900 1.000 ) )" //
                + " amb ( 0.200 )" //
                + " dif ( 0.800 )" //
                + " spc ( 0.700 1.000 0.000 ) " //
                + "}");

        assertEquals("#01", atr.getName());
        assertEquals(0.6, atr.getCol().red);
        assertEquals(0.2, atr.getAmb());
        assertEquals(0.8, atr.getDif());
        assertEquals(1.0, atr.getSpc()[1]);
    }

    public void testParse2() throws Exception {
        Atr atr = AtrLineParser.parse("1: atr noname {" + //
                " col ( 1.000 )" + //
                " amb ( 0.200 )" + //
                " dif ( 0.800 )" + //
                " spc ( 0.700 1.000 0.000 )" + //
                " colormap ( \"L3\\effect\\horizon.png\" )" + //
                " mapsize ( 0 0 1 1 ) " + //
                "}");

        assertEquals("noname", atr.getName());
        assertEquals(1.0, atr.getCol().red);
        assertEquals(1.0, atr.getCol().green);
        assertEquals(1.0, atr.getCol().blue);
        assertEquals("L3\\effect\\horizon.png", atr.getColorMap1());
        assertEquals(0.0, atr.getMapSize()[0]);
        assertEquals(1.0, atr.getMapSize()[3]);
    }

    public void testParse3() throws Exception {
        Atr atr = AtrLineParser
                .parse("1: atr \".zあいうえお\" {" //
                        + " col ( rgb ( 0.410 0.180 0.180 ) )" //
                        + " amb ( 0.500 )" //
                        + " dif ( 0.500 )" //
                        + " colormap ( \"L3\\nature\\rain\\rain[1-4].png\" )" //
                        + " colormap ( \"C:\\Documents and Settings\\narazaki.daishi\\My Documents\\top_left.9.png\" )" //
                        + " bumpmap ( mecha1.pic )" //
                        + " tramap ( \"L3\\machine\\nightcity.png\" )" //
                        + " spcmap ( mecha2.pic )" //
                        + " refmap ( \"L3\\machine\\mecha3bump.png\" )" //
                        + " glowpowermap ( \"L3\\machine\\mecha4.png\" )" //
                        + " mapsize ( 0 0 1 1 )" //
                        + " opt ( " //
                        + "    celllookedge " //
                        + "    draw 0.000 " //
                        + "    rate 0.005 " //
                        + "    shader 3 " //
                        + "        ( 0.000 0.500 0.250 ) " //
                        + "        ( 0.500 0.626 0.750 ) " //
                        + "        ( 0.626 1.000 1.000 )" //
                        + " ) " //
                        + "}");

        assertEquals("L3\\nature\\rain\\rain[1-4].png", atr.getColorMap1());
    }

    public void testParse4() throws Exception {
        Atr atr = AtrLineParser.parse("3: atr \"力\" {" //
                + " col ( rgb ( 1.000 0.700 0.150 ) )" //
                + " amb ( 1.000 )" //
                + " dif ( 0.000 )" //
                + " tra ( 0.070 )" //
                + " ref ( 0.030 )" //
                + " spc ( 0.000 1.200 0.000 ) " //
                + "}");

        assertEquals("力", atr.getName());
        assertEquals(0.07, atr.getTra());
    }

    public void testParse5() throws Exception {
        Atr atr = AtrLineParser.parse("4: atr \"力2\" {" //
                + " col ( 1.000 )" //
                + " amb ( 1.000 )" //
                + " dif ( 0.000 )" //
                + " opt ( emittion 0.000 5.000 ) " //
                + "}");

        assertEquals(0.0, atr.getOptEmittion()[0]);
        assertEquals(5.0, atr.getOptEmittion()[1]);
    }

    public void testParse6() throws Exception {
        AtrLineParser.parse("3: atr \"あにめ\" {" //
                + " col ( 1.000 )" //
                + " amb ( 0.500 )" //
                + " dif ( 0.500 )" //
                + " spc ( 1.000 0.010 0.000 )" //
                + " opt ( " //
                + "    castshadow " //
                + "    receiveshadow " //
                + "    celllookedge " //
                + "    celllookspecular 0.927184 0.94466 " //
                + "    draw rgb ( 0.600 0.900 1.000 ) " //
                + "    rate 0.010 " //
                + "    shader 3" //
                + "        ( 0.000 0.577313 rgb ( 0.950 1.000 0.150 ) )" //
                + "        ( 0.632639 0.828407 rgb ( 0.700 0.100 0.800 ) )" //
                + "        ( 0.893082 10.000 rgb ( 0.150 0.200 0.250 ) )" //
                + " ) " //
                + "}");
    }

}
