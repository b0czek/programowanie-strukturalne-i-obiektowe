package Uno.Network.Utilities;

import java.io.ByteArrayOutputStream;

public class PromiscousByteArrayOutputStream extends ByteArrayOutputStream {

    public PromiscousByteArrayOutputStream() {

    }

    public PromiscousByteArrayOutputStream(int size) {
        super(size);
    }

    public int getCount() {
        return count;
    }

    public byte[] getBuf() {
        return buf;
    }
}
