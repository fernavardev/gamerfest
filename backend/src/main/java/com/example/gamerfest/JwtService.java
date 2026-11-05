
package com.example.gamerfest;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

@Service
public class JwtService {

    private final SecretKey clave = Keys.hmacShaKeyFor(
            JwtConstants.TOKEN_SECRET.getBytes(StandardCharsets.UTF_8));

    public String generarToken(Usuario usuario) {

        long ahora = System.currentTimeMillis();

        return Jwts.builder()
                .subject(usuario.getUsername())
                .issuer(JwtConstants.TOKEN_ISSUER)
                .claim("rol", usuario.getRol())
                .issuedAt(new Date(ahora))
                .expiration(new Date(
                        ahora + JwtConstants.TOKEN_EXPIRATION_TIME))
                .signWith(clave, Jwts.SIG.HS256)
                .compact();
    }

    public Claims validarToken(String token) {

        return Jwts.parser()
                .verifyWith(clave)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
