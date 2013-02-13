package jp.dev7.enchant.doga.converter.pic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PicLoader {
    private static class ColorCache {
        int color;
        int next;
        int prev;
    }

    private BitStream in;

    private int colorBit;
    private int width;
    private int height;
    private ColorCache[] table;
    private int colorP;
    private int[][] canvas;
    private int[][] chainTable;

    public PicLoader() {
        table = new ColorCache[128];
        for (int i = 0; i < 128; i++) {
            table[i] = new ColorCache();
            table[i].color = 0;
            table[i].prev = i + 1;
            table[i].next = i - 1;
        }
        table[127].prev = 0;
        table[0].next = 127;
        colorP = 0;
    }

    public BufferedImage load(String picFileName) throws IOException {
        return load(new File(picFileName));
    }

    public BufferedImage load(File file) throws IOException {
        if (in != null) {
            throw new IllegalStateException("this loader is already in use");
        }

        in = new BitStream(new FileInputStream(file));
        try {
            if ((char) in.loadBit(8) != 'P') {
                throw new UnsupportedImageTypeException("invalid header.", file); // P
            }
            if ((char) in.loadBit(8) != 'I') {
                throw new UnsupportedImageTypeException("invalid header.", file); // I
            }
            if ((char) in.loadBit(8) != 'C') {
                throw new UnsupportedImageTypeException("invalid header.", file); // C
            }
            if (in.loadBit(8) != 0x1a) {
                throw new UnsupportedImageTypeException("invalid header.", file); // 1A コメントの終わり
            }
            if (in.loadBit(8) != 0) {
                throw new UnsupportedImageTypeException("invalid header.", file); // 0 真のコメントの終わり
            }
            if (in.loadBit(8) != 0) {
                throw new UnsupportedImageTypeException("invalid header.", file); // 0 予約
            }
            if (in.loadBit(8) != 0) {
                throw new UnsupportedImageTypeException(
                        "unsupported machine type.", file); // 機種タイプと機種ごとのモード
            }
            colorBit = in.loadBit(16);
            if (colorBit != 15 && colorBit != 16) {
                throw new UnsupportedImageTypeException(
                        "unsupported color bit(" + colorBit + ").", file); // 色のビット数
            }
            width = in.loadBit(16);
            height = in.loadBit(16);

            canvas = new int[width][height];
            chainTable = new int[width][height];

            expand();

            return createImage();

        } finally {
            in.close();
        }
    }

    private BufferedImage createImage() {
        final BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D graphics = (Graphics2D) image.getGraphics();

        int shift = colorBit - 15;
        for (int x = 0; x < canvas.length; x++) {
            for (int y = 0; y < canvas[x].length; y++) {
                int c = canvas[x][y];
                float g = (c >> (10 + shift)) & 0x1f;
                float r = (c >> (5 + shift)) & 0x1f;
                float b = (c >> (0 + shift)) & 0x1f;
                int a = (colorBit == 16) ? (c & 0x01) : 1;
                graphics.setColor(new Color(r / 31, g / 31, b / 31, 1 - a));
                graphics.fillRect(x, y, 1, 1);
            }
        }
        return image;
    }

    private void expand() throws IOException {
        int x = -1;
        int y = 0;
        int c = 0;
        long l;

        for (;;) {
            l = readLen();
            while (--l != 0) {
                if (++x == width) {
                    if (++y == height) {
                        return;
                    }
                    x = 0;
                }
                if (chainTable[x][y] != 0) {
                    c = canvas[x][y];
                }

                canvas[x][y] = c;
            }

            if (++x == width) {
                if (++y == height) {
                    return;
                }
                x = 0;
            }

            c = readColor();

            canvas[x][y] = c;

            if (in.loadBit(1) != 0) {
                expandChain(x, y, c);
            }
        }
    }

    private void expandChain(int x, int y, int c) throws IOException {
        for (;;) {
            switch (in.loadBit(2)) {
            case 0:
                if (in.loadBit(1) == 0) {
                    return;
                }
                if (in.loadBit(1) == 0) {
                    x -= 2;
                } else {
                    x += 2;
                }
                break;
            case 1:
                x--;
                break;
            case 2:
                break;
            case 3:
                x++;
                break;
            }

            if (++y >= height || x < 0 || x >= width) {
                return;
            }

            canvas[x][y] = c;
            chainTable[x][y] = 1;
        }
    }

    private int newColor(int c) {
        colorP = table[colorP].prev;
        table[colorP].color = c;
        return c;
    }

    private int getColor(int idx) {
        if (colorP != idx) {
            table[table[idx].prev].next = table[idx].next;
            table[table[idx].next].prev = table[idx].prev;

            table[table[colorP].prev].next = idx;
            table[idx].prev = table[colorP].prev;
            table[colorP].prev = idx;
            table[idx].next = colorP;

            colorP = idx;
        }

        return table[idx].color;
    }

    private int readColor() throws IOException {
        if (in.loadBit(1) == 0) {
            return newColor(in.loadBit(colorBit));
        } else {
            return getColor(in.loadBit(7));
        }
    }

    private long readLen() throws IOException {
        int a = 1;
        while (in.loadBit(1) != 0) {
            a++;
        }

        return (in.loadBit(a) + (1 << a) - 1);
    }

}
