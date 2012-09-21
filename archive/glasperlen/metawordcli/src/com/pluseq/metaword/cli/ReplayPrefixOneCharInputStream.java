package com.pluseq.metaword.cli;

import java.io.*;

/**
 * This is awkward and inefficient, but probably the minimal way to add
 * UTF-8 support to JLine
 *
 * @author <a href="mailto:Marc.Herbert@continuent.com">Marc Herbert</a>
 */

class ReplayPrefixOneCharInputStream extends InputStream {
    byte firstByte;
    int byteLength;
    InputStream wrappedStream;
    int byteRead;

    final String encoding;
    
    public ReplayPrefixOneCharInputStream(String encoding) {
        this.encoding = encoding;
    }
    
    public void setInput(int recorded, InputStream wrapped) throws IOException {
        this.byteRead = 0;
        this.firstByte = (byte) recorded;
        this.wrappedStream = wrapped;

        byteLength = 1;
        if (encoding.equalsIgnoreCase("UTF-8"))
            setInputUTF8(recorded, wrapped);
        else if (encoding.equalsIgnoreCase("UTF-16"))
            byteLength = 2;
        else if (encoding.equalsIgnoreCase("UTF-32"))
            byteLength = 4;
    }
        
        
    public void setInputUTF8(int recorded, InputStream wrapped) throws IOException {
        // 110yyyyy 10zzzzzz
        if ((firstByte & (byte) 0xE0) == (byte) 0xC0)
            this.byteLength = 2;
        // 1110xxxx 10yyyyyy 10zzzzzz
        else if ((firstByte & (byte) 0xF0) == (byte) 0xE0)
            this.byteLength = 3;
        // 11110www 10xxxxxx 10yyyyyy 10zzzzzz
        else if ((firstByte & (byte) 0xF8) == (byte) 0xF0)
            this.byteLength = 4;
        else
            throw new IOException("invalid UTF-8 first byte: " + firstByte);
    }

    public int read() throws IOException {
        if (available() == 0)
            return -1;

        byteRead++;

        if (byteRead == 1)
            return firstByte;

        return wrappedStream.read();
    }

    /**
    * InputStreamReader is greedy and will try to read bytes in advance. We
    * do NOT want this to happen since we use a temporary/"losing bytes"
    * InputStreamReader above, that's why we hide the real
    * wrappedStream.available() here.
    */
    public int available() {
        return byteLength - byteRead;
    }
}
