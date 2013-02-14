package jp.dev7.enchant.doga.parser.data;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * SUFファイル内のパーツ単体(obj)に相当.
 */
public class Obj {

    private String name;
    private final List<Prim> primitives = Lists.newArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Prim> getPrimitives() {
        return primitives;
    }

    @Override
    public String toString() {
        return "Obj [name=" + name + ", primitives=" + primitives + "]";
    }

}
