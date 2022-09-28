package groupId;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpClient {

    private HttpMessage response;
    private final int statusCode;

    public HttpClient(String host, int port, String requestTarget) throws IOException {

        /*
            Når vi instansierer ett socket object så kan host være en ip adresse. URL må kun presiseres i
            requesten. IP er landet og url er byen vi skal til.
         */

        Socket socket = new Socket(host, port);
        String request =
                "GET " + requestTarget + " HTTP/1.1\r\n" +
                "Connection: close\r\n" +
                "Host: " + host + " \r\n" +
                "\r\n";
        socket.getOutputStream().write(request.getBytes());


        response = new HttpMessage(socket);
        String line = response.getStartLine();
        //System.out.println(line);
        statusCode = Integer.parseInt(line.split(" ")[1]);


    }

    public static void main(String[] args) throws IOException {
        var client = new HttpClient("httpbin.org",80,"/html");
        System.out.println(client.getBody());
        Socket socket = new Socket("httpbin.org", 80);
        String hei;
        // alt + j kan man markere alle lignende ord eller betegnelser og markere dem.
        String request = "GET /html HTTP/1.1\r\n" +
                "Connection: close\r\n" +
                "Host: httpbin.org\r\n" +
                "\r\n";
        socket.getOutputStream().write(request.getBytes(StandardCharsets.UTF_8));// lim in i getbytes

        int c;
        while((c = socket.getInputStream().read()) != -1 ){
            System.out.print((char)c);

        }
    }

    public int getStatusCode() {

        return this.statusCode;
    }

    public String getHeader(String fieldName) {
        return response.getHeader(fieldName);
    }

    public int getContentLength() {

        return response.contentLength;
    }

    public String getBody() {

        return response.body;
    }
}
