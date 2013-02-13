package jp.dev7.enchant.doga.parser.data;

import java.util.List;
import java.util.Map;

import jp.dev7.enchant.doga.parser.atr.data.Atr;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * ユニットファイル(*.fsc, *.l2p, *.l3p, *.e1p)に相当.
 */
public class Unit {

    private final List<UnitObj> objects = Lists.newArrayList();
    private final Map<String, Atr> palette = Maps.newHashMap();

    public List<UnitObj> getObjects() {
        return objects;
    }

    public Map<String, Atr> getPalette() {
        return palette;
    }

}
