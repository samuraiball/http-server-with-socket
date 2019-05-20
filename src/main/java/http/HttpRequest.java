package http;

import util.IOUtil;

import java.io.IOException;
import java.io.InputStream;

public class HttpRequest {

    private static HttpHeader httpHeader;
    private static String bodyText;

    public HttpRequest(InputStream in) {
        try {
            httpHeader = new HttpHeader(in);
            bodyText = readBody(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String readBody(InputStream in) throws IOException {
        if (httpHeader.isChunkedTransfer()) {
            return readBodyByChunkedEncoding(in);
        } else {
            return readBodyByContentLength(in);
        }
    }

    private String readBodyByContentLength(InputStream in) throws IOException {

        final int contentLength = httpHeader.getContentLength();
        if (contentLength <= 0) {
            return null;
        }
        byte[] buffer = new byte[contentLength];
        in.read(buffer);

        return IOUtil.toString(buffer);
    }

    private String readBodyByChunkedEncoding(InputStream in) throws IOException {
        StringBuilder body = new StringBuilder();

        int chunkSize = Integer.parseInt(IOUtil.readLine(in), 16);

        while (chunkSize != 0) {
            byte[] buffer = new byte[chunkSize];
            in.read(buffer);

            body.append(IOUtil.toString(buffer));

            // chunk-bodyの末尾のCRLFを読み飛ばす。
            IOUtil.readLine(in);
            chunkSize = Integer.parseInt(IOUtil.readLine(in), 16);
        }

        return body.toString();

    }


    private int getContentLength() {
        return httpHeader.getContentLength();
    }

    public String getHeaderText() {
        return httpHeader.getText();
    }

    public String getBodyText() {
        return bodyText;
    }

}
