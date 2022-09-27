package groupId;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpClientTest {

    @Test

    void shouldGetStatusCodeOk() throws IOException {

        var client = new HttpClient("httpbin.org",80,"/html");

        assertEquals(200, client.getStatusCode());
    }

    @Test

    void shouldGetStatusCodeNotFound() throws IOException {

        var client = new HttpClient("httpbin.org",80,"/bullshit");

        assertEquals(404, client.getStatusCode());

    }

    @Test

    void shouldGetFailureResponseCode() throws IOException {

        var client = new HttpClient("httpbin.org",80,"/status/403");

        assertEquals(403, client.getStatusCode());

    }
    @Test

    void shouldReadResponseHeaders() throws IOException {

        var client = new HttpClient("httpbin.org",80,"/html");

        assertEquals("text/html; charset=utf-8", client.getHeader("Content-Type"));
        assertEquals("close", client.getHeader("Connection"));

    }
    @Test

    void shouldReadContentLength() throws IOException {

        var client = new HttpClient("httpbin.org",80,"/html");

        assertEquals("3741", client.getHeader("Content-Length"));
        assertEquals(3741, client.getContentLength());

    }
    @Test

    void shouldReadBody() throws IOException {

        var client = new HttpClient("httpbin.org",80,"/html");

        String body = client.getBody();

        assertTrue(body.startsWith("<!DOCTYPE html>"));
        assertTrue(body.endsWith(" </body>\n</html>"));
        assertEquals(body.length(), client.getContentLength());

    }




    }

