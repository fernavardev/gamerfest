package com.example.gamerfest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtCookieService {

    public void escribirToken(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie
                .from(JwtConstants.TOKEN_COOKIE_NAME, token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(JwtConstants.TOKEN_EXPIRATION_TIME / 1000)
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void borrarToken(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie
                .from(JwtConstants.TOKEN_COOKIE_NAME, "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
