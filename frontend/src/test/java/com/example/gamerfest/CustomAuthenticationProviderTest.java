package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

class CustomAuthenticationProviderTest {

    @Test
    void deberiaAutenticarAsignarRolYGuardarToken() throws Exception {
        BackendApiClient client = mock(BackendApiClient.class);
        TokenStore tokenStore = new TokenStore();

        CustomAuthenticationProvider provider =
                new CustomAuthenticationProvider(client, tokenStore);

        when(client.login("usuario", "password-test"))
                .thenReturn(new LoginResponse("token-test", "ADMIN"));

        MockHttpServletRequest request = new MockHttpServletRequest();

        RequestContextHolder.setRequestAttributes(
                new ServletRequestAttributes(request));

        try {
            var credentials =
                    UsernamePasswordAuthenticationToken.unauthenticated(
                            "usuario", "password-test");

            var authentication = provider.authenticate(credentials);

            assertTrue(authentication.isAuthenticated());
            assertEquals("usuario", authentication.getName());
            assertTrue(authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));

            assertEquals("token-test",
                    tokenStore.requireToken(request.getSession()));

        } finally {
            RequestContextHolder.resetRequestAttributes();
        }
    }
}