package http;

import util.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpHeader {

    private final String headerText;
    private Map<String, String> messageHeaders = new HashMap<>();
    private static final String CRLF = "\r\n";

    public HttpHeader(InputStream inputStream) throws IOException {
        StringBuilder header = new StringBuilder();

        header.append(readRequestLine(inputStream))
                .append(readMassageLine(inputStream));

        headerText = header.toString();
    }

    private String readRequestLine(InputStream in) throws IOException {
        return IOUtil.readLine(in);
    }

    private StringBuilder readMassageLine(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();

        String messageLine = IOUtil.readLine(in);

        while (!messageLine.isEmpty()) {
            putMassageLine(messageLine);
            sb.append(messageLine + CRLF);
            messageLine = IOUtil.readLine(in);
        }
        return sb;
    }

    private void putMassageLine(String messageLine) {
        String[] tmp = messageLine.split(":");
        String key = tmp[0].trim();
        String value = tmp[1].trim();
        messageHeaders.put(key, value);
    }

    public String getText(){
        return headerText;
    }
    public int getContentLength() {
        return Integer.parseInt(messageHeaders.getOrDefault("Content-Length", "0"));
    }

    public boolean isChunkedTransfer() {
        return this.messageHeaders.getOrDefault("Transfer-Encoding", "-").equals("chunked");
    }
}
