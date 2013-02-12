package jp.dev7.enchant.doga.parser.pose.data;

import java.util.List;

import com.google.common.collect.Lists;

public class Pose {

    private String name;
    private final List<String> labels = Lists.newArrayList();
    private final List<double[]> movs = Lists.newArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<double[]> getUnitPose() {
        return movs;
    }

}
