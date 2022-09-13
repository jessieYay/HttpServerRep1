package groupId;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpClient {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World");
        Socket socket = new Socket("httpbin.org", 80);
        String hei;
        String request = ("GET / HTTP/1.1\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n" +
                "Accept-Language: nb-NO,nb;q=0.9,no;q=0.8,nn;q=0.7,en-US;q=0.6,en;q=0.5\n" +
                "Cache-Control: max-age=0\n" +
                "Connection: keep-alive\n" +
                "Host: httpbin.org\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36");
        socket.getOutputStream().write(request.getBytes(StandardCharsets.UTF_8));
    }
}
