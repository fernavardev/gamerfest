package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

class JwtAuthorizationFilterTest {

    @Test
    void deberiaAutenticarUsuarioConTokenValido() throws Exception {
        JwtService jwtService = mock(JwtService.class);
        UserDetailsService userDetailsService =
                mock(UserDetailsService.class);

        JwtAuthorizationFilter filter =
                new JwtAuthorizationFilter(jwtService, userDetailsService);

        Claims claims = mock(Claims.class);
        Usuario usuario = new Usuario("usuario", "hash-test", "ADMIN");

        when(jwtService.validarToken("token-test")).thenReturn(claims);
        when(claims.getIssuer()).thenReturn(JwtConstants.TOKEN_ISSUER);
        when(claims.getSubject()).thenReturn("usuario");
        when(userDetailsService.loadUserByUsername("usuario"))
                .thenReturn(usuario);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        request.addHeader("Authorization", "Bearer token-test");

        SecurityContextHolder.clearContext();

        try {
            filter.doFilterInternal(request, response, chain);

            var authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            assertNotNull(authentication);
            assertTrue(authentication.isAuthenticated());
            assertEquals("usuario", authentication.getName());
            assertTrue(authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));

            verify(chain).doFilter(request, response);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }
}