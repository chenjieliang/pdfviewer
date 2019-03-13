package com.cjl.pdfviewer.library.code;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;

public abstract class CharacterEncoder {
    protected PrintStream pStream;

    public CharacterEncoder() {
    }

    protected abstract int bytesPerAtom();

    protected abstract int bytesPerLine();

    protected void encodeBufferPrefix(OutputStream paramOutputStream) throws IOException {
        this.pStream = new PrintStream(paramOutputStream);
    }

    protected void encodeBufferSuffix(OutputStream paramOutputStream) throws IOException {
    }

    protected void encodeLinePrefix(OutputStream paramOutputStream, int paramInt) throws IOException {
    }

    protected void encodeLineSuffix(OutputStream paramOutputStream) throws IOException {
        this.pStream.println();
    }

    protected abstract void encodeAtom(OutputStream var1, byte[] var2, int var3, int var4) throws IOException;

    protected int readFully(InputStream paramInputStream, byte[] paramArrayOfByte) throws IOException {
        for(int i = 0; i < paramArrayOfByte.length; ++i) {
            int j = paramInputStream.read();
            if (j == -1) {
                return i;
            }

            paramArrayOfByte[i] = (byte)j;
        }

        return paramArrayOfByte.length;
    }

    public void encode(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
        byte[] arrayOfByte = new byte[this.bytesPerLine()];
        this.encodeBufferPrefix(paramOutputStream);

        while(true) {
            int j = this.readFully(paramInputStream, arrayOfByte);
            if (j == 0) {
                break;
            }

            this.encodeLinePrefix(paramOutputStream, j);

            for(int i = 0; i < j; i += this.bytesPerAtom()) {
                if (i + this.bytesPerAtom() <= j) {
                    this.encodeAtom(paramOutputStream, arrayOfByte, i, this.bytesPerAtom());
                } else {
                    this.encodeAtom(paramOutputStream, arrayOfByte, i, j - i);
                }
            }

            if (j < this.bytesPerLine()) {
                break;
            }

            this.encodeLineSuffix(paramOutputStream);
        }

        this.encodeBufferSuffix(paramOutputStream);
    }

    public void encode(byte[] paramArrayOfByte, OutputStream paramOutputStream) throws IOException {
        ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
        this.encode((InputStream)localByteArrayInputStream, paramOutputStream);
    }

    public String encode(byte[] paramArrayOfByte) {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
        String str = null;

        try {
            this.encode((InputStream)localByteArrayInputStream, localByteArrayOutputStream);
            str = localByteArrayOutputStream.toString("8859_1");
            return str;
        } catch (Exception var6) {
            throw new Error("CharacterEncoder.encode internal error");
        }
    }

    private byte[] getBytes(ByteBuffer paramByteBuffer) {
        byte[] localObject = null;
        if (paramByteBuffer.hasArray()) {
            byte[] arrayOfByte = paramByteBuffer.array();
            if (arrayOfByte.length == paramByteBuffer.capacity() && arrayOfByte.length == paramByteBuffer.remaining()) {
                localObject = arrayOfByte;
                paramByteBuffer.position(paramByteBuffer.limit());
            }
        }

        if (localObject == null) {
            localObject = new byte[paramByteBuffer.remaining()];
            paramByteBuffer.get((byte[])localObject);
        }

        return localObject;
    }

    public void encode(ByteBuffer paramByteBuffer, OutputStream paramOutputStream) throws IOException {
        byte[] arrayOfByte = this.getBytes(paramByteBuffer);
        this.encode(arrayOfByte, paramOutputStream);
    }

    public String encode(ByteBuffer paramByteBuffer) {
        byte[] arrayOfByte = this.getBytes(paramByteBuffer);
        return this.encode(arrayOfByte);
    }

    public void encodeBuffer(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
        byte[] arrayOfByte = new byte[this.bytesPerLine()];
        this.encodeBufferPrefix(paramOutputStream);

        int j;
        do {
            do {
                j = this.readFully(paramInputStream, arrayOfByte);
            } while(j == 0);

            this.encodeLinePrefix(paramOutputStream, j);

            for(int i = 0; i < j; i += this.bytesPerAtom()) {
                if (i + this.bytesPerAtom() <= j) {
                    this.encodeAtom(paramOutputStream, arrayOfByte, i, this.bytesPerAtom());
                } else {
                    this.encodeAtom(paramOutputStream, arrayOfByte, i, j - i);
                }
            }

            this.encodeLineSuffix(paramOutputStream);
        } while(j >= this.bytesPerLine());

        this.encodeBufferSuffix(paramOutputStream);
    }

    public void encodeBuffer(byte[] paramArrayOfByte, OutputStream paramOutputStream) throws IOException {
        ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
        this.encodeBuffer((InputStream)localByteArrayInputStream, paramOutputStream);
    }

    public String encodeBuffer(byte[] paramArrayOfByte) {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);

        try {
            this.encodeBuffer((InputStream)localByteArrayInputStream, localByteArrayOutputStream);
        } catch (Exception var5) {
            throw new Error("CharacterEncoder.encodeBuffer internal error");
        }

        return localByteArrayOutputStream.toString();
    }

    public void encodeBuffer(ByteBuffer paramByteBuffer, OutputStream paramOutputStream) throws IOException {
        byte[] arrayOfByte = this.getBytes(paramByteBuffer);
        this.encodeBuffer(arrayOfByte, paramOutputStream);
    }

    public String encodeBuffer(ByteBuffer paramByteBuffer) {
        byte[] arrayOfByte = this.getBytes(paramByteBuffer);
        return this.encodeBuffer(arrayOfByte);
    }
}
