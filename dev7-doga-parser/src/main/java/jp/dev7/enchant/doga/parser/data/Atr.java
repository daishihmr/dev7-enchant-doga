package jp.dev7.enchant.doga.parser.data;

import java.io.File;
import java.util.Arrays;

public class Atr {

    private String name;
    private Color col = new Color();
    private double amb;
    private double dif;
    /**
     * 光沢.
     * 
     * 0:光沢色 0.0(物体の色) - 1.0(光源の色)<br>
     * 1:鏡面係数 0.0(でかい) - 1.0(小さい)<br>
     * 2:？
     */
    private double[] spc = new double[3];
    private double tra;
    private double ref;
    private String colorMap1;
    private File colorMap1File;
    private String colorMap2;
    private String bumpMap;
    private String traMap;
    private String spcMap;
    private String refMap;
    private String glowPowerMap;
    private double[] mapSize = { 0, 0, 255, 255 };

    private boolean optCastShadow;
    private boolean optReceiveShadow;
    private boolean optCellLookEdge;
    private double[] optCellLookSpecular = new double[2];
    private double[] optDraw = new double[3];
    private double optRate;
    private int optShader;
    private double[] optShader1 = new double[5];
    private double[] optShader2 = new double[5];
    private double[] optShader3 = new double[5];
    private double[] optEmittion = new double[2];

    public Atr clone() {
        final Atr c = new Atr();

        c.name = name;
        c.col = new Color(col.red, col.green, col.blue);
        c.amb = amb;
        c.dif = dif;
        c.spc = Arrays.copyOf(spc, 3);
        c.tra = tra;
        c.ref = ref;
        c.colorMap1 = colorMap1;
        c.colorMap1File = colorMap1File;
        c.colorMap2 = colorMap2;
        c.bumpMap = bumpMap;
        c.traMap = traMap;
        c.spcMap = spcMap;
        c.refMap = refMap;
        c.glowPowerMap = glowPowerMap;
        c.mapSize = Arrays.copyOf(mapSize, 4);

        c.optCastShadow = optCastShadow;
        c.optReceiveShadow = optReceiveShadow;
        c.optCellLookEdge = optCellLookEdge;
        c.optCellLookSpecular = Arrays.copyOf(optCellLookSpecular, 2);
        c.optDraw = Arrays.copyOf(optDraw, 3);
        c.optRate = optRate;
        c.optShader = optShader;
        c.optShader1 = Arrays.copyOf(optShader1, 5);
        c.optShader2 = Arrays.copyOf(optShader2, 5);
        c.optShader3 = Arrays.copyOf(optShader3, 5);
        c.optEmittion = Arrays.copyOf(optEmittion, 2);

        return c;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTra() {
        return tra;
    }

    public void setTra(double tra) {
        this.tra = tra;
    }

    public Color getCol() {
        return col;
    }

    public void setCol(Color col) {
        this.col = col;
    }

    public double getAmb() {
        return amb;
    }

    public void setAmb(double amb) {
        this.amb = amb;
    }

    public double getDif() {
        return dif;
    }

    public void setDif(double dif) {
        this.dif = dif;
    }

    public double[] getSpc() {
        return spc;
    }

    public void setSpc(double[] spc) {
        this.spc = spc;
    }

    public double getRef() {
        return ref;
    }

    public void setRef(double ref) {
        this.ref = ref;
    }

    public String getColorMap1() {
        return colorMap1;
    }

    public void setColorMap1(String colormap1) {
        this.colorMap1 = colormap1;
    }

    public File getColorMap1File() {
        return colorMap1File;
    }

    public void setColorMap1File(File colorMap1File) {
        this.colorMap1File = colorMap1File;
    }

    public String getColorMap2() {
        return colorMap2;
    }

    public void setColorMap2(String colormap2) {
        this.colorMap2 = colormap2;
    }

    public String getBumpMap() {
        return bumpMap;
    }

    public void setBumpMap(String bumpmap) {
        this.bumpMap = bumpmap;
    }

    public String getTraMap() {
        return traMap;
    }

    public void setTraMap(String tramap) {
        this.traMap = tramap;
    }

    public String getSpcMap() {
        return spcMap;
    }

    public void setSpcMap(String spcmap) {
        this.spcMap = spcmap;
    }

    public String getRefMap() {
        return refMap;
    }

    public void setRefMap(String refmap) {
        this.refMap = refmap;
    }

    public String getGlowPowerMap() {
        return glowPowerMap;
    }

    public void setGlowPowerMap(String glowpowermap) {
        this.glowPowerMap = glowpowermap;
    }

    public double[] getMapSize() {
        return mapSize;
    }

    public void setMapSize(double[] mapSize) {
        this.mapSize = mapSize;
    }

    public boolean isOptCastShadow() {
        return optCastShadow;
    }

    public void setOptCastShadow(boolean optCastShadow) {
        this.optCastShadow = optCastShadow;
    }

    public boolean isOptReceiveShadow() {
        return optReceiveShadow;
    }

    public void setOptReceiveShadow(boolean optReceiveShadow) {
        this.optReceiveShadow = optReceiveShadow;
    }

    public boolean isOptCellLookEdge() {
        return optCellLookEdge;
    }

    public void setOptCellLookEdge(boolean optCelllookedge) {
        this.optCellLookEdge = optCelllookedge;
    }

    public double[] getOptCellLookSpecular() {
        return optCellLookSpecular;
    }

    public void setOptCellLookSpecular(double[] optCelllookspecular) {
        this.optCellLookSpecular = optCelllookspecular;
    }

    public double[] getOptDraw() {
        return optDraw;
    }

    public void setOptDraw(double[] optDraw) {
        this.optDraw = optDraw;
    }

    public double getOptRate() {
        return optRate;
    }

    public void setOptRate(double optRate) {
        this.optRate = optRate;
    }

    public int getOptShader() {
        return optShader;
    }

    public void setOptShader(int optShader) {
        this.optShader = optShader;
    }

    public double[] getOptShader1() {
        return optShader1;
    }

    public void setOptShader1(double[] optShader1) {
        this.optShader1 = optShader1;
    }

    public double[] getOptShader2() {
        return optShader2;
    }

    public void setOptShader2(double[] optShader2) {
        this.optShader2 = optShader2;
    }

    public double[] getOptShader3() {
        return optShader3;
    }

    public void setOptShader3(double[] optShader3) {
        this.optShader3 = optShader3;
    }

    public double[] getOptEmittion() {
        return optEmittion;
    }

    public void setOptEmittion(double[] optEmittion) {
        this.optEmittion = optEmittion;
    }

    @Override
    public String toString() {
        return "Atr [name=" + name + ", col=" + col + ", amb=" + amb + ", dif="
                + dif + ", spc=" + Arrays.toString(spc) + ", colorMap1="
                + colorMap1 + ", mapSize=" + Arrays.toString(mapSize)
                + ", optEmittion=" + Arrays.toString(optEmittion) + "]";
    }

}
