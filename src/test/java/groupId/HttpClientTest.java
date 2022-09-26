package groupId;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpClientTest {

    @Test

    void shouldGetStatusCodeOk(){

        var client = new HttpClient("httpbin.org",80,"/html");

        assertEquals(200, client.getStatusCode());
    }

    @Test

    void shouldGetStatusCodeNotFound(){

        var client = new HttpClient("httpbin.org",80,"/bullshit");

        assertEquals(404, client.getStatusCode());

    }




    }

