
package com.example.gamerfest;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final BackendApiClient backendApiClient;
    private final TokenStore tokenStore;

    public CustomAuthenticationProvider(
            BackendApiClient backendApiClient,
            TokenStore tokenStore) {
        this.backendApiClient = backendApiClient;
        this.tokenStore = tokenStore;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        String username = authentication.getName();
        Object credentials = authentication.getCredentials();

        if (username == null || username.isBlank() || credentials == null) {
            throw new BadCredentialsException("Credenciales incorrectas");
        }

        try {
            String token = backendApiClient.login(username, credentials.toString());

            ServletRequestAttributes requestAttributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (requestAttributes == null) {
                throw new AuthenticationServiceException(
                        "No se encontró la solicitud de autenticación.");
            }

            HttpServletRequest request = requestAttributes.getRequest();
            tokenStore.save(request.getSession(true), token);

            return UsernamePasswordAuthenticationToken.authenticated(
                    username,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_USER")));

        } catch (RestClientResponseException e) {
            throw new BadCredentialsException("Credenciales incorrectas", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authentication);
    }
}
