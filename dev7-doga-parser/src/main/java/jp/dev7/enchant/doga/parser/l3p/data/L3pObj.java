package jp.dev7.enchant.doga.parser.l3p.data;

import java.util.Arrays;

/**
 * L3Pファイル内のobjに相当.
 */
public class L3pObj extends jp.dev7.enchant.doga.parser.fsc.data.FscObj {

    private String paletteName;

    public String getPaletteName() {
        return paletteName.toLowerCase();
    }

    public void setPaletteName(String paletteName) {
        this.paletteName = paletteName;
    }

    @Override
    public String toString() {
        return "Part [paletteName=" + paletteName + ", getName()=" + getName()
                + ", getSufFileName()=" + getSufFileName() + ", getMov()="
                + Arrays.toString(getMov()) + ", getRotz()=" + getRotz()
                + ", getRoty()=" + getRoty() + ", getRotx()=" + getRotx()
                + ", getScal()=" + Arrays.toString(getScal()) + ", getClass()="
                + getClass() + "]";
    }

}
