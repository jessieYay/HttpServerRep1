package groupId;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer {

    private ServerSocket serverSocket;

    public HttpServer(int port) throws IOException {

        serverSocket = new ServerSocket(port);
        start();


    }

    private void start() {

        new Thread(() -> {
            try {
                var clientSocket = serverSocket.accept();
                handleClient(clientSocket);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("Server started");
    }

    private void handleClient(Socket clientSocket) throws IOException {
        var request = new HttpMessage(clientSocket);
        var requestResponse = request.getStartLine().split(" ")[1];
        var responseBody = "Unknown URL " + requestResponse + "";
        clientSocket.getOutputStream().write(("HTTP/1.1 404 NOT FOUND\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: " + responseBody.length() + "\r\n" +
                "\r\n" +
                responseBody +
                "\r\n").getBytes(StandardCharsets.UTF_8));
    }

    public int getPort() {
        //getLocalPort er en innebygd metode i serversocket objektet, med den porten serveren faktisk bruker.
        return serverSocket.getLocalPort();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");

        var serverSocket = new ServerSocket(9080);

        var clientSocket = serverSocket.accept();




        var body = "<html><h1>Hello world and Jessie!</h1></html>";
        var contentLength = body.getBytes().length;

        clientSocket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +
                "Date: Wed, 28 Sep 2022 13:23:50 GMT\r\n" +
                "Content-Type: text/html; charset=utf-8\r\n" +
                "Content-Length " + contentLength + "\r\n" +
                "Connection: keep-alive\r\n" +
                "\r\n" +
                body ).getBytes(StandardCharsets.UTF_8));

        // text/html; charset=utf-8
        // text/plain;

    }


}
