package jp.dev7.enchant.doga.parser.data;

/**
 * ユニットファイル(*.fsc, *.l2p, *.l3p, *.e1p)内のobjに相当.
 */
public class UnitObj {

    private String name;
    private String sufFileName;
    private double[] mov = new double[3];
    private double rotz;
    private double roty;
    private double rotx;
    private double[] scal = new double[3];
    private String paletteName;

    public double[] getMov() {
        return mov;
    }

    public String getName() {
        return name;
    }

    public String getPaletteName() {
        if (paletteName != null) {
            return paletteName.toLowerCase();
        } else {
            return null;
        }
    }

    public double getRotx() {
        return rotx;
    }

    public double getRoty() {
        return roty;
    }

    public double getRotz() {
        return rotz;
    }

    public double[] getScal() {
        return scal;
    }

    public String getSufFileName() {
        return sufFileName.toLowerCase();
    }

    public void setMov(double[] mov) {
        this.mov = mov;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPaletteName(String paletteName) {
        this.paletteName = paletteName;
    }

    public void setRotx(double rotx) {
        this.rotx = rotx;
    }

    public void setRoty(double roty) {
        this.roty = roty;
    }

    public void setRotz(double rotz) {
        this.rotz = rotz;
    }

    public void setScal(double[] scal) {
        this.scal = scal;
    }

    public void setSufFileName(String sufFileName) {
        if (sufFileName == null) {
            throw new NullPointerException("sufFileName null");
        }
        this.sufFileName = sufFileName;
    }

}
