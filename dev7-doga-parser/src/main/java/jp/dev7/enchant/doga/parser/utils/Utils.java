package jp.dev7.enchant.doga.parser.utils;

import java.io.File;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class Utils {
    private Utils() {
    }

    public static <T> List<T> scanDir(File file, Function<File, T> f) {
        final List<T> result = Lists.newArrayList();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                result.addAll(scanDir(child, f));
            }
        } else {
            T t = f.apply(file);
            if (t != null) {
                result.add(t);
            }
        }
        return result;
    }
}
