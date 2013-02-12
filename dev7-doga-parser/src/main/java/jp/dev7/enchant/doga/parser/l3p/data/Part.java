package jp.dev7.enchant.doga.parser.l3p.data;

import java.util.Arrays;

public class Part {

    private String name;
    private String sufFileName;
    private String paletteName;
    private double[] mov = new double[3];
    private double rotz;
    private double roty;
    private double rotx;
    private double[] scal = new double[3];

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSufFileName() {
        return sufFileName.toLowerCase();
    }

    public void setSufFileName(String sufFileName) {
        if (sufFileName == null) {
            throw new NullPointerException("sufFileName null");
        }
        this.sufFileName = sufFileName;
    }

    public String getPaletteName() {
        return paletteName.toLowerCase();
    }

    public void setPaletteName(String paletteName) {
        this.paletteName = paletteName;
    }

    public double[] getMov() {
        return mov;
    }

    public void setMov(double[] mov) {
        this.mov = mov;
    }

    public double getRotz() {
        return rotz;
    }

    public void setRotz(double rotz) {
        this.rotz = rotz;
    }

    public double getRoty() {
        return roty;
    }

    public void setRoty(double roty) {
        this.roty = roty;
    }

    public double getRotx() {
        return rotx;
    }

    public void setRotx(double rotx) {
        this.rotx = rotx;
    }

    public double[] getScal() {
        return scal;
    }

    public void setScal(double[] scal) {
        this.scal = scal;
    }

    @Override
    public String toString() {
        return "L3pObj [name=" + name + ", sufFileName=" + sufFileName
                + ", atrName=" + paletteName + ", mov=" + Arrays.toString(mov)
                + ", rotz=" + rotz + ", roty=" + roty + ", rotx=" + rotx
                + ", scal=" + Arrays.toString(scal) + "]";
    }

}
