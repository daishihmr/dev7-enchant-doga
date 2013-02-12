package jp.dev7.enchant.doga.converter;

import java.util.List;

import com.google.common.collect.Lists;

public class EnchantArticulated {

    private EnchantUnit root;
    private final List<EnchantPose> poses = Lists.newArrayList();

    public EnchantUnit getRoot() {
        return root;
    }

    public void setRoot(EnchantUnit root) {
        this.root = root;
    }

    public List<EnchantPose> getPoses() {
        return poses;
    }

}
