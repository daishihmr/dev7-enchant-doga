package jp.dev7.enchant.doga.parser.l3p.data;

import java.util.List;
import java.util.Map;

import jp.dev7.enchant.doga.parser.atr.data.Atr;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class L3p {

    private final List<Part> objects = Lists.newArrayList();
    private final Map<String, Atr> palette = Maps.newHashMap();

    public List<Part> getObjects() {
        return objects;
    }

    public Map<String, Atr> getPalette() {
        return palette;
    }

    @Override
    public String toString() {
        return "L3pData [objects=" + objects + "]";
    }

}
