package com.db.taskcrud.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.db.taskcrud.exception.BadRequestException;
import com.db.taskcrud.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    public String secret;

    public String generateToken(Person person){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("taskcrud")
                    .withSubject(person.getEmail())
                    .withExpiresAt(getExpirationTime(3600))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new BadRequestException("Error creating JWT Token");
        }
    }

    public String generateRefreshToken(Person person) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth0")
                    .withSubject(person.getId().toString())
                    .withExpiresAt(getExpirationTime(7200))
                    .sign(algorithm);
        } catch (JWTVerificationException exception){
            throw new BadRequestException("Error creating Refresh Token");
        }
    }

    public String verifyToken(String token){
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();

            decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch(JWTVerificationException exception){
            throw new BadRequestException("Error verifying JWT Token");
        }
    }

    private Instant getExpirationTime(Integer seconds){
        return LocalDateTime.now().plusSeconds(seconds).toInstant(ZoneOffset.of("-03:00"));
    }
}
