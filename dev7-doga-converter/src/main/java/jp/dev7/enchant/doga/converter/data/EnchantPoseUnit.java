package jp.dev7.enchant.doga.converter.data;

import java.util.List;

import jp.dev7.enchant.doga.converter.JsonHintFormat;

import net.arnx.jsonic.JSONHint;

import com.google.common.collect.Lists;

public class EnchantPoseUnit {

    private double[] pose;
    private double[] quat;

    private final List<EnchantPoseUnit> childUnits = Lists.newArrayList();

    /**
     * rotX, rotY, rotZ, movX, movY, movZ
     */
    @JSONHint(format = JsonHintFormat.N)
    public double[] getPose() {
        return pose;
    }

    public void setPose(double[] pose) {
        this.pose = pose;
    }

    @JSONHint(format = JsonHintFormat.N)
    public double[] getQuat() {
        return quat;
    }

    public void setQuat(double[] quat) {
        this.quat = quat;
    }

    public List<EnchantPoseUnit> getChildUnits() {
        return childUnits;
    }

}
