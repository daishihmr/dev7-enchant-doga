package jp.dev7.enchant.doga.parser.l2p;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import jp.dev7.enchant.doga.parser.AtrFileParser;
import jp.dev7.enchant.doga.parser.atr.autogen.ParseException;
import jp.dev7.enchant.doga.parser.data.Atr;
import jp.dev7.enchant.doga.parser.data.Color;
import jp.dev7.enchant.doga.parser.util.Props;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class L2pAtrs {

    private final Map<String, Color> colors = Maps.newHashMap();
    private final Map<String, String> textures = Maps.newHashMap();
    private final Map<String, Atr> materials = Maps.newHashMap();

    private final Map<String, Atr> cache = Maps.newHashMap();

    public L2pAtrs() throws ParseException, IOException {
        colors.put("C1", new Color(1.00, 1.00, 1.00));
        colors.put("C5A", new Color(0.91, 0.81, 0.82));
        colors.put("C6A", new Color(0.95, 0.85, 0.92));
        colors.put("C8A", new Color(0.90, 0.83, 0.92));
        colors.put("C10A", new Color(0.85, 0.84, 0.93));
        colors.put("C9A", new Color(0.93, 0.97, 0.98));
        colors.put("C13A", new Color(0.79, 0.87, 0.80));
        colors.put("C15A", new Color(0.91, 0.95, 0.83));
        colors.put("C17A", new Color(0.96, 0.96, 0.86));
        colors.put("C18A", new Color(0.95, 0.91, 0.85));
        colors.put("C19A", new Color(0.93, 0.83, 0.81));
        colors.put("C2B", new Color(0.85, 0.85, 0.80));
        colors.put("C5B", new Color(0.89, 0.59, 0.60));
        colors.put("C4", new Color(0.95, 0.64, 0.86));
        colors.put("C7", new Color(0.83, 0.60, 0.87));
        colors.put("C10B", new Color(0.67, 0.64, 0.92));
        colors.put("C9B", new Color(0.83, 0.94, 0.98));
        colors.put("C12", new Color(0.54, 0.80, 0.59));
        colors.put("C15B", new Color(0.84, 0.95, 0.59));
        colors.put("C16", new Color(0.94, 0.96, 0.64));
        colors.put("C20", new Color(0.95, 0.84, 0.63));
        colors.put("C19B", new Color(0.93, 0.63, 0.57));
        colors.put("C2", new Color(0.65, 0.65, 0.70));
        colors.put("C5", new Color(0.90, 0.10, 0.15));
        colors.put("C6", new Color(1.00, 0.15, 0.75));
        colors.put("C8", new Color(0.70, 0.10, 0.80));
        colors.put("C10", new Color(0.30, 0.20, 0.95));
        colors.put("C9", new Color(0.60, 0.90, 1.00));
        colors.put("C13", new Color(0.00, 0.70, 0.15));
        colors.put("C15", new Color(0.70, 1.00, 0.05));
        colors.put("C17", new Color(0.95, 1.00, 0.15));
        colors.put("C18", new Color(1.00, 0.70, 0.15));
        colors.put("C19", new Color(1.00, 0.20, 0.05));
        colors.put("C2D", new Color(0.40, 0.40, 0.45));
        colors.put("C5D", new Color(0.58, 0.13, 0.16));
        colors.put("C6D", new Color(0.68, 0.20, 0.54));
        colors.put("C8D", new Color(0.49, 0.16, 0.55));
        colors.put("C11", new Color(0.26, 0.20, 0.63));
        colors.put("C9D", new Color(0.49, 0.66, 0.72));
        colors.put("C14", new Color(0.05, 0.45, 0.14));
        colors.put("C15D", new Color(0.50, 0.67, 0.14));
        colors.put("C17D", new Color(0.67, 0.69, 0.22));
        colors.put("C18D", new Color(0.68, 0.51, 0.20));
        colors.put("C21", new Color(0.64, 0.19, 0.11));
        colors.put("C3", new Color(0.15, 0.20, 0.25));
        colors.put("C5E", new Color(0.43, 0.16, 0.18));
        colors.put("C6E", new Color(0.54, 0.26, 0.46));
        colors.put("C8E", new Color(0.41, 0.21, 0.44));
        colors.put("C10E", new Color(0.26, 0.23, 0.48));
        colors.put("C9E", new Color(0.48, 0.58, 0.61));
        colors.put("C13E", new Color(0.09, 0.33, 0.14));
        colors.put("C15E", new Color(0.43, 0.53, 0.21));
        colors.put("C17E", new Color(0.55, 0.57, 0.28));
        colors.put("C18E", new Color(0.54, 0.44, 0.26));
        colors.put("C22", new Color(0.47, 0.21, 0.16));

        textures.put("NO", null);
        textures.put("T10", "meisai1.pic");
        textures.put("T31", "check.pic");
        textures.put("T26", "mizutama.pic");
        textures.put("T5", "renga2.pic");
        textures.put("T20", "fractal.pic");
        textures.put("T8", "butubutu.pic");
        textures.put("T12", "bakuhatu.pic");
        textures.put("M1", "mecha1.pic");
        textures.put("T11", "meisai2.pic");
        textures.put("T30", "stripe.pic");
        textures.put("T27", "ichimatu.pic");
        textures.put("T4", "isigaki.pic");
        textures.put("T23", "yogore.pic");
        textures.put("T3", "siwa.pic");
        textures.put("T32", "ami1.pic");
        textures.put("M2", "mecha2.pic");
        textures.put("T6", "mokume1.pic");
        textures.put("T9", "sima.pic");
        textures.put("T22", "jyutan.pic");
        textures.put("T21", "kawara.pic");
        textures.put("T19", "sabi.pic");
        textures.put("T25", "fuzzy.pic");
        textures.put("T17", "mesh1.pic");
        textures.put("M3", "mecha3.pic");
        textures.put("T7", "mokume2.pic");
        textures.put("T16", "hyou.pic");
        textures.put("T28", "hanagara.pic");
        textures.put("T24", "koishi.pic");
        textures.put("T29", "randam.pic");
        textures.put("T15", "kemuri.pic");
        textures.put("T18", "mesh2.pic");

        final List<Atr> mlist = AtrFileParser.parse(new File(Props.commonDir(),
                "atr/material.atr"));
        final Map<String, Atr> atrmap = Maps.uniqueIndex(mlist,
                new Function<Atr, String>() {

                    @Override
                    public String apply(Atr input) {
                        return input.getName();
                    }

                });

        materials.put("M1", atrmap.get("Normal"));
        materials.put("M2", atrmap.get("Smooth"));
        materials.put("M3", atrmap.get("Shine"));
        materials.put("M4", atrmap.get("Rough"));
        materials.put("M5", atrmap.get("Metallic"));
        materials.put("M6", atrmap.get("Glass"));
        materials.put("M7", atrmap.get("FrostedGlass"));
        materials.put("M8", atrmap.get("Emittion1"));
        materials.put("M9", atrmap.get("FakeShadow"));
    }

    public Atr getAtr(String name) {
        if (cache.get(name) != null) {
            return cache.get(name);
        }

        final String[] names = name.split(":");

        final Atr result = materials.get(names[2]).clone();
        result.setName(name);

        result.setCol(colors.get(names[0]));
        if (textures.get(names[1]) != null) {
            result.setColorMap1(textures.get(names[1]));
        }

        cache.put(name, result);
        return result;
    }
}
