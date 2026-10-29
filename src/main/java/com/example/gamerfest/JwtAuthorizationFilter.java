package com.example.gamerfest;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader(
                JwtConstants.HEADER_AUTHORIZATION_KEY
        );

        if (authorization != null &&
                authorization.startsWith(JwtConstants.TOKEN_BEARER_PREFIX)) {

            String token = authorization.substring(
                    JwtConstants.TOKEN_BEARER_PREFIX.length()
            );

            try {
                Claims claims = jwtService.validarToken(token);

                if (JwtConstants.TOKEN_ISSUER.equals(claims.getIssuer())) {

                    String username = claims.getSubject();

                    UserDetails usuario = userDetailsService
                            .loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken autenticacion =
                            new UsernamePasswordAuthenticationToken(
                                    usuario,
                                    null,
                                    usuario.getAuthorities()
                            );

                    SecurityContextHolder.getContext()
                            .setAuthentication(autenticacion);
                }

            } catch (JwtException |
                     org.springframework.security.core.AuthenticationException e) {

                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
