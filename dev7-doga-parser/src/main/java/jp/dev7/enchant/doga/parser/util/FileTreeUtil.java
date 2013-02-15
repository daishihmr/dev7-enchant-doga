package jp.dev7.enchant.doga.parser.util;

import java.io.File;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class FileTreeUtil {
    private FileTreeUtil() {
    }

    public static <T> List<T> scanDir(File file, Function<File, T> toFile) {
        return scanDir(file, new Function<File, T>() {
            @Override
            public T apply(File input) {
                return null;
            }
        }, toFile);
    }

    public static <T> List<T> scanDir(File file, Function<File, T> toDir,
            Function<File, T> toFile) {
        final List<T> result = Lists.newArrayList();
        if (file.isDirectory()) {
            T t = toDir.apply(file);
            if (t != null) {
                result.add(t);
            }
            for (File child : file.listFiles()) {
                result.addAll(scanDir(child, toDir, toFile));
            }
        } else {
            T t = toFile.apply(file);
            if (t != null) {
                result.add(t);
            }
        }
        return result;
    }

}
