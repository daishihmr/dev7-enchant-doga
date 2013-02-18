package jp.dev7.enchant.doga.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import jp.dev7.enchant.doga.converter.PicLoader;
import jp.dev7.enchant.doga.parser.util.Props;
import junit.framework.TestCase;

public class UtilsTest extends TestCase {

    void testToDataURL() throws IOException {
        PicLoader loader = new PicLoader();
        BufferedImage image = loader.load(new File(Props.commonDir(),
                "atr/ami1.pic"));
        System.out.println(Utils.toDataURL(image));
    }

}
