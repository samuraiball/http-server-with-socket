package http;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpHeaderTest {

    private static final String CRLF = "\r\n";

    String httpRequest =
            "GET / HTTP/1.1Host: localhost:8080" + CRLF +
                    "ser-Agent: curl/7.63.0" + CRLF +
                    "Accept: */*" + CRLF + CRLF;
    String httpPostRequest =
            "POST / HTTP/1.1Host: localhost:8080" + CRLF +
                    "User-Agent: curl/7.63.0" + CRLF +
                    "Accept: */*" + CRLF +
                    "Content-Length: 52" + CRLF +
                    "Content-Type: application/x-www-form-urlencoded" + CRLF + CRLF +
                    "Message Bodyurl";


    @Test
    void normal_getContentLength() throws Exception {

        InputStream in = new ByteArrayInputStream(httpPostRequest.getBytes(StandardCharsets.UTF_8));

        HttpHeader httpHeader = new HttpHeader(in);
        int contentLength = httpHeader.getContentLength();

        assertThat(contentLength).isEqualTo(52);
    }

    @Test
    void dose_not_have_contentLength_getContentLength() throws Exception {

        InputStream in =
                new ByteArrayInputStream(httpRequest.getBytes(StandardCharsets.UTF_8));

        HttpHeader httpHeader = new HttpHeader(in);
        int contentLength = httpHeader.getContentLength();

        assertThat(contentLength).isEqualTo(0);
    }


    @Test
    void dose_not_have_contentLength_isChunkedTransfer() throws Exception {

        InputStream in =
                new ByteArrayInputStream(httpRequest.getBytes(StandardCharsets.UTF_8));

        HttpHeader httpHeader = new HttpHeader(in);
        assertFalse(httpHeader.isChunkedTransfer());

    }
}