package groupId;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpMessage {

    private final Map<String,String> headers;
    private final String startLine;
    String body;
    int contentLength;

    public HttpMessage(Socket socket) throws IOException {
        startLine = readLine(socket);
        headers = readHeaders(socket);
        if(getHeader("Content-Length") != null){
            contentLength = Integer.parseInt(getHeader("Content-Length"));
            body = readBody(socket);
        }
        /*
            Bygger stringen som body skal inneholde.
            Men vi må transformere den via ett byte array på lengde med contentlength.
            Vi mister litt i oversetningen hvis vi gjør det direkte til string.
         */

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

    private Map<String, String> readHeaders(Socket socket) throws IOException {
        Map<String,String> headers = new HashMap<>();
        String headerLine;
        while(!(headerLine = readLine(socket)).isEmpty()){
            String[] parts = headerLine.split(":\\s*");
            headers.put(parts[0], parts[1]);
        }
        return headers;
    }

    private String readBody(Socket socket) throws IOException {
        var body = new byte[contentLength];
        for(int i = 0; i < body.length; i++){
            body[i] = (byte) socket.getInputStream().read();// Filling the body one character at a time.
        }
        String temp = new String(body, StandardCharsets.UTF_8);
        return temp;
    }

    public String getHeader(String fieldName) {
        return this.headers.get(fieldName);
    }

    public String getStartLine() {
        return this.startLine;
    }


}
