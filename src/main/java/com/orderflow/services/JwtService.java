package com.orderflow.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    // Criado uma única vez para evitar recriar o algoritmo a cada requisição
    private Algorithm algorithm;
    // Verificador reutilizável para validar assinatura e issuer
    private JWTVerifier verifier;

    @PostConstruct
    public void init() {
        // Inicializa após o Spring injetar o secret
        this.algorithm = Algorithm.HMAC256(secret);
        // Configura o verificador padrão da aplicação
        this.verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
    }

    public String generateAccessToken(String email) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(email)
                .withClaim("type", "access")
                .withExpiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
                .sign(algorithm);
    }

    public String generateRefreshToken(String email) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(email)
                .withClaim("type", "refresh")
                .withExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS))
                .sign(algorithm);
    }

    public String validateAccessToken(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);

            if (!"access".equals(decodedJWT.getClaim("type").asString())) {
                throw new RuntimeException("Invalid token type");
            }

            return decodedJWT.getSubject();

        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid or expired token");
        }
    }

    public String validateRefreshToken(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);

            if (!"refresh".equals(decodedJWT.getClaim("type").asString())) {
                throw new RuntimeException("Invalid token type");
            }

            return decodedJWT.getSubject();

        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid or expired token");
        }
    }
}