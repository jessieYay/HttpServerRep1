package groupId;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {

    private final Path serverRoot = Path.of("target","test-files");

    @Test

    void shouldRespondWith404ToUnknownUrl() throws IOException {
        var server = new HttpServer(0, Path.of("."));
        var client = new HttpClient("localhost", server.getPort(), "/unknown-url");

        assertEquals(404,client.getStatusCode());
        assertEquals("Unknown URL /unknown-url",client.getBody());
    }
    @Test

    void shouldRespondWith200ToKnownUrl() throws IOException {
        Files.createDirectories(serverRoot);
        Path file = serverRoot.resolve("example-file-txt");
        var content = "Hello there " + LocalDateTime.now();
        Files.writeString(file,content);
        var server = new HttpServer(0, serverRoot);
        var client = new HttpClient("localhost", server.getPort(), "/" + file.getFileName());

        assertEquals(200,client.getStatusCode());
        assertEquals(content,client.getBody());
    }
    @Test
    void shouldHandleMoreThanOneRequest() throws IOException {
        var server = new HttpServer(0, serverRoot);
        Path file = serverRoot.resolve("index.html");
        Files.writeString(file,"<h1>Hello Jessie from ultimate and world!</h1>");
        assertEquals(200,new HttpClient("localhost", server.getPort(), "/" + file.getFileName())
                .getStatusCode()
        );
        assertEquals(200,new HttpClient("localhost", server.getPort(), "/" + file.getFileName())
                .getStatusCode()
        );
    }
    @Test
    void shouldReturn500OnUnhandledErrors() throws IOException {
        var server = new HttpServer(0, serverRoot);
        assertEquals(500,new HttpClient("localhost", server.getPort(), "/foo*bar")
                .getStatusCode()
        );
    }

    @Test
    void shouldHandleEchoRequest() throws IOException {
        var server = new HttpServer(0, serverRoot);
        assertEquals("Hallæ world + galaxy & universe",
                new HttpClient("localhost", server.getPort(), "/api/echo?input-string=Hallæ+world+%2B+galaxy+%26+universe")
                .getBody()
        );

    }

    @Test
    void shouldListIndexFileForDirectory() throws IOException {
        Files.createDirectories(serverRoot);
        Path file = serverRoot.resolve("index.html");
        var content = "Hello there " + LocalDateTime.now();
        Files.writeString(file,content);
        var server = new HttpServer(0, serverRoot);
        var client = new HttpClient("localhost", server.getPort(), "/");

        assertEquals(content,client.getBody());

    }

    @Test
    void shouldGive404ForMissingIndexFileInDirectory() throws IOException {
        Files.createDirectories(serverRoot.resolve("something"));
        var server = new HttpServer(0, serverRoot);
        var client = new HttpClient("localhost", server.getPort(), "/something/");

        assertEquals(404,client.getStatusCode());



    }

}