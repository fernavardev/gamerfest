package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class BackendApiClientTest {

    @Test
    void deberiaObtenerTokenYRolDesdeBackend() throws Exception {
        RestClient.Builder builder = RestClient.builder();

        MockRestServiceServer server =
                MockRestServiceServer.bindTo(builder).build();

        BackendApiClient client = new BackendApiClient(
                builder, "http://localhost:8081", new TokenStore());

        server.expect(requestTo("http://localhost:8081/login"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(
                        "{\"username\":\"usuario\",\"password\":\"password-test\"}"))
                .andRespond(withSuccess(
                        "{\"token\":\"token-test\",\"rol\":\"ADMIN\"}",
                        MediaType.APPLICATION_JSON));

        LoginResponse respuesta =
                client.login("usuario", "password-test");

        assertEquals("token-test", respuesta.token());
        assertEquals("ADMIN", respuesta.rol());

        server.verify();
    }
}