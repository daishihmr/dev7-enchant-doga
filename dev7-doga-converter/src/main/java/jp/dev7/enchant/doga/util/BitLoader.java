package jp.dev7.enchant.doga.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * 入力ストリームから1ビットずつ読み出すためのユーティリティ.
 */
public class BitLoader {

    private final InputStream in;
    private int buf = -1;
    private int bufP = -1;

    public BitLoader(InputStream in) {
        this.in = in;
    }

    public int nextBit() throws IOException {
        if (bufP == -1) {
            buf = in.read();
            if (buf == -1) {
                return -1;
            }
            bufP = 0;
        }
        int data = (buf >> (7 - bufP)) & 1;
        bufP++;
        if (bufP == 8) {
            bufP = -1;
        }
        return data;
    }

    public int loadBits(int len) throws IOException {
        int data = 0;
        for (int i = 0; i < len; i++) {
            int b = nextBit();
            if (b == -1) {
                throw new IOException("data is ended.");
            }
            data = data << 1 | b;
        }
        return data;
    }

    public void close() {
        try {
            in.close();
        } catch (IOException e) {
        }
    }

    public static String bin(int data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0, end = 32; i < end; i++) {
            buf.insert(0, data & 1);
            data = data >> 1;
        }
        return buf.toString();
    }

    public static String bin(int data, int len) {
        return bin(data).substring(32 - len);
    }

}
