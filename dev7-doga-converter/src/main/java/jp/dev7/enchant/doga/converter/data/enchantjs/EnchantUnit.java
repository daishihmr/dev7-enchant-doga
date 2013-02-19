package jp.dev7.enchant.doga.converter.data.enchantjs;

import java.util.List;

import jp.dev7.enchant.doga.converter.JsonHintFormat;

import net.arnx.jsonic.JSONHint;

import com.google.common.collect.Lists;

public class EnchantUnit {
    private List<EnchantMesh> l3p;
    private double[] basePosition;
    private final List<EnchantUnit> childUnits = Lists.newArrayList();

    public List<EnchantMesh> getL3p() {
        return l3p;
    }

    public void setL3p(List<EnchantMesh> l3p) {
        this.l3p = l3p;
    }

    @JSONHint(format = JsonHintFormat.N)
    public double[] getBasePosition() {
        return basePosition;
    }

    public void setBasePosition(double[] basePosition) {
        this.basePosition = basePosition;
    }

    public List<EnchantUnit> getChildUnits() {
        return childUnits;
    }

}
