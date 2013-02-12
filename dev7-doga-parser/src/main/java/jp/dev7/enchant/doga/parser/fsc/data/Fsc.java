package jp.dev7.enchant.doga.parser.fsc.data;

import java.util.List;

import com.google.common.collect.Lists;

public class Fsc {

    private final List<Part> objects = Lists.newArrayList();

    public List<Part> getObjects() {
        return objects;
    }

    @Override
    public String toString() {
        return "Fsc [objects=" + objects + "]";
    }

}
