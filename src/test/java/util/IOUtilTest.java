package util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Test;

import exception.EmptyRequestException;

public class IOUtilTest {

    @Test
    void readLine() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("foo\r\nbar".getBytes());
        String line = IOUtil.readLine(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        in.transferTo(out);
        assertAll(() -> {
            assertEquals("foo", line);
            assertEquals("bar", out.toString());
        });
    }

    @Test
    void readLine_not_contains_crlf() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("foobar".getBytes());
        assertThrows(EmptyRequestException.class, () -> {
            IOUtil.readLine(in);
        });
    }
}
