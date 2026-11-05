
package com.example.gamerfest;

public final class JwtConstants {

    private JwtConstants() {
    }

    public static final String LOGIN_URL = "/login";

    public static final String HEADER_AUTHORIZATION_KEY = "Authorization";

    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    public static final String TOKEN_ISSUER = "GamerFest";

    public static final long TOKEN_EXPIRATION_TIME = 3_600_000L;

    public static final String TOKEN_SECRET = "GamerFest2026ClaveSecretaJWTParaAutenticacionSegura";

}
