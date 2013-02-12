package tools;

import java.io.File;

import com.google.common.base.Function;

public class ToLowerCase {

    public static void main(String[] args) {
        scanDir(new File(args[0]), new Function<File, Void>() {
            @Override
            public Void apply(File input) {

                input.renameTo(new File(input.getParent(), input.getName()
                        .toLowerCase()));

                return null;
            }
        });
    }

    private static void scanDir(File file, Function<File, Void> f) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                scanDir(child, f);
            }
        } else {
            f.apply(file);
        }
    }

}
