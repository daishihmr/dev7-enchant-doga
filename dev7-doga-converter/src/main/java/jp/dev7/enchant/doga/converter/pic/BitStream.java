package jp.dev7.enchant.doga.converter.pic;

import java.io.IOException;
import java.io.InputStream;

public class BitStream {

    private InputStream in;
    private int buf = -1;
    private int bufP = -1;

    public BitStream(InputStream in) {
        this.in = in;
    }

    public int next1bit() throws IOException {
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

    public int loadBit(int len) throws IOException {
        int data = 0;
        for (int i = 0; i < len; i++) {
            int b = next1bit();
            if (b == -1) {
                throw new IOException("data is lost.");
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
}
