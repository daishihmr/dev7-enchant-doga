package jp.dev7.enchant.doga.converter.pic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jp.dev7.enchant.doga.parser.Props;
import jp.dev7.enchant.doga.parser.utils.Utils;
import junit.framework.TestCase;

import com.google.common.base.Function;

public class PicLoaderTest extends TestCase {

    public void testLoadFile() throws IOException {
        Utils.scanDir(new File(Props.commonDir(), "atr"),
                new Function<File, Void>() {
                    @Override
                    public Void apply(File input) {
                        if (input.getName().toLowerCase().endsWith(".pic")) {
                            try {
                                BufferedImage image = new PicLoader()
                                        .load(input);
                                ImageIO.write(image, "png", new File("target",
                                        input.getName() + ".png"));

                                System.out.println(input.getAbsolutePath()
                                        + " loaded.");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }
                });
    }

}
