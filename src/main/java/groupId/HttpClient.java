package groupId;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {

    private int statusCode;
    private Map<String,String> headers = new HashMap<>();
    private int contentLength;
    private String body;

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

        String line = readLine(socket);
        System.out.println(line);
        statusCode = Integer.parseInt(line.split(" ")[1]);


        
        String headerLine;
        while(!(headerLine = readLine(socket)).isEmpty()){
            String[] parts = headerLine.split(":\\s*");
            headers.put(parts[0], parts[1]);
        }
        contentLength = Integer.parseInt(getHeader("Content-Length"));

    }

    private String readLine(Socket socket) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while((c = socket.getInputStream().read()) != '\r'){
            line.append((char)c);
        }
        c = socket.getInputStream().read(); // Må lese en linje til pga \n.
        return line.toString();
    }

    public static void main(String[] args) throws IOException {

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
        return headers.get(fieldName);
    }

    public int getContentLength() {

        return this.contentLength;
    }

    public String getBody() {

        return body;
    }
}
