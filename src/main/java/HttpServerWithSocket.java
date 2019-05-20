import http.HttpRequest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServerWithSocket {

    private static final int PORT = 8080;
    private static final String CRLF = "\r\n";


    public static void main(String[] args) {
        try (
                ServerSocket ss = new ServerSocket(PORT);
                Socket socket = ss.accept();
                InputStream in = socket.getInputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {

            HttpRequest hr = new HttpRequest(in);
            System.out.println(hr.getHeaderText());
            System.out.println(hr.getBodyText());

            bw.write("HTTP/1.1 200 OK" + CRLF);
            bw.write("Content-Type: text/html" + CRLF);
            bw.write(CRLF);
            bw.write("<h1>Hello World!!</h1>");

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("<<<< end");
    }
}
