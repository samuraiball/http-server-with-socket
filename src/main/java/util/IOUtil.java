package util;

import exception.EmptyRequestException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class IOUtil {

    private static final String CRLF = "\r\n";

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    public static void println(OutputStream out, String line) {
        print(out, line + CRLF);
    }

    public static void print(OutputStream out, String line) {
        try {
            out.write(line.getBytes(UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String readLine(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while (true) {
            int b = in.read();
            if (b == -1) {
                throw new EmptyRequestException();
            } else if (b == '\r') {
                b = in.read();
                if (b == '\n') {
                    return out.toString("UTF-8");
                } else if (b == -1) {
                    throw new EmptyRequestException();
                }
                out.write('\r');
            }
            out.write(b);
        }
    }

    public static InputStream toInputStream(String string) {
        return new ByteArrayInputStream(string.getBytes(UTF_8));
    }

    public static String toString(byte[] buffer) {
        return new String(buffer, UTF_8);
    }

    private IOUtil() {
    }
}
