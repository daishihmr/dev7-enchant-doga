package jp.dev7.enchant.doga.parser.fsc.data;

import java.util.List;

import com.google.common.collect.Lists;

public class Fsc {

    private final List<FscObj> objects = Lists.newArrayList();

    public List<FscObj> getObjects() {
        return objects;
    }

    @Override
    public String toString() {
        return "Fsc [objects=" + objects + "]";
    }

}
