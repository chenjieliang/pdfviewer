package com.cjl.pdfviewer.library.code;

import java.io.IOException;
import java.io.OutputStream;

public class BASE64Encoder extends CharacterEncoder {
    private static final char[] pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/', '='};

    public BASE64Encoder() {
    }

    protected int bytesPerAtom() {
        return 3;
    }

    protected int bytesPerLine() {
        return 57;
    }

    protected void encodeAtom(OutputStream paramOutputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
        byte i;
        if (paramInt2 == 1) {
            i = paramArrayOfByte[paramInt1];
            int j = 0;
            //int k = false;
            paramOutputStream.write(pem_array[i >>> 2 & 63]);
            paramOutputStream.write(pem_array[(i << 4 & 48) + (j >>> 4 & 15)]);
            paramOutputStream.write(61);
            paramOutputStream.write(61);
        } else {
            byte j;
            if (paramInt2 == 2) {
                i = paramArrayOfByte[paramInt1];
                j = paramArrayOfByte[paramInt1 + 1];
                int k = 0;
                paramOutputStream.write(pem_array[i >>> 2 & 63]);
                paramOutputStream.write(pem_array[(i << 4 & 48) + (j >>> 4 & 15)]);
                paramOutputStream.write(pem_array[(j << 2 & 60) + (k >>> 6 & 3)]);
                paramOutputStream.write(61);
            } else {
                i = paramArrayOfByte[paramInt1];
                j = paramArrayOfByte[paramInt1 + 1];
                int k = paramArrayOfByte[paramInt1 + 2];
                paramOutputStream.write(pem_array[i >>> 2 & 63]);
                paramOutputStream.write(pem_array[(i << 4 & 48) + (j >>> 4 & 15)]);
                paramOutputStream.write(pem_array[(j << 2 & 60) + (k >>> 6 & 3)]);
                paramOutputStream.write(pem_array[k & 63]);
            }
        }

    }
}
