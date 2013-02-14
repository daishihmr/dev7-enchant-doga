package tools;

import java.io.File;

import jp.dev7.enchant.doga.parser.utils.Utils;

import com.google.common.base.Function;

public class ToLowerCase {

    public static void main(String[] args) {
        final String baseDir = args[0];
        final Function<File, Void> toLowerCase = new Function<File, Void>() {
            @Override
            public Void apply(File input) {
                File dest = new File(baseDir, input.getAbsolutePath()
                        .replace(baseDir, "").toLowerCase());
                System.out.println("    " + input.getAbsolutePath() + "\n -> "
                        + dest.getAbsolutePath() + "\n");
                input.renameTo(dest);

                return null;
            }
        };
        Utils.scanDir(new File(args[0]), toLowerCase, toLowerCase);
    }

}
