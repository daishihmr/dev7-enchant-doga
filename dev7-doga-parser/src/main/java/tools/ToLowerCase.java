package tools;

import java.io.File;

import jp.dev7.enchant.doga.parser.utils.Utils;

import com.google.common.base.Function;

public class ToLowerCase {

    public static void main(String[] args) {
        Utils.scanDir(new File(args[0]), new Function<File, Void>() {
            @Override
            public Void apply(File input) {

                File dest = new File(input.getParent(), input.getName()
                        .toLowerCase());
                System.out.println(input.getAbsolutePath() + "\n\t -> "
                        + dest.getAbsolutePath());
                input.renameTo(dest);

                return null;
            }
        });
    }

}
