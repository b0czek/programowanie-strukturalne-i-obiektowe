package Uno.Network.Utilities;

import Uno.Network.Server.Message.Message;

import java.io.ByteArrayInputStream;

public class PosByteArrayInputStream extends ByteArrayInputStream {
    public PosByteArrayInputStream(byte[] buf, int offset, int length) {
        super(buf,offset,length);
    }
    public boolean seekHeader() {
        byte[] headerBytes =Message.MESSAGE_HEADER.getBytes();
        while(pos < buf.length - headerBytes.length) {
            while(pos < buf.length && buf[pos] != headerBytes[0]) {
                pos++;
            }
            if(pos == buf.length) {
                return false;
            }

            for(int i = 0; i < headerBytes.length; i++) {
                if(buf[pos + i] != headerBytes[i]) {
                    return false;
                }
            }
            pos += headerBytes.length;
            return true;

        }



        return false;
    }

    public int getPos() {
        return this.pos;
    }

}
