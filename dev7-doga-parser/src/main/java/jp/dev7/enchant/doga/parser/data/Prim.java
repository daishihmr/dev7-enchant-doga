package jp.dev7.enchant.doga.parser.data;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * SUFファイル内の面データ(prim)に相当.
 */
public class Prim {

    private String atrName;
    private final List<Vertex> vertices = Lists.newArrayList();

    public String getAtrName() {
        return atrName;
    }

    public void setAtrName(String atrName) {
        if (atrName == null) {
            this.atrName = null;
            return;
        }
        this.atrName = atrName.toLowerCase();
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        return "Prim [atrName=" + atrName + ", vertices=" + vertices + "]";
    }

}
