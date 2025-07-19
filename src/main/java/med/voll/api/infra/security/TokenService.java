package med.voll.api.infra.security;/*
 * Copyright (c) 2025 yober cieza coronel. Todos los derechos reservados.
 *
 * Este archivo es parte de proyect-spring-security.
 *
 * proyect-spring-security es software propietario: no puedes redistribuirlo y/o modificarlo sin el
 * permiso expreso del propietario. Está sujeto a los términos y condiciones
 * que acompañan el uso del software.
 *
 * Cualquier uso no autorizado puede ser sancionado según la ley vigente.
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    public String getAccesToken(Usuario usuario){


        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("Api voil.api")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(generateTime())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("error al generar el token"+exception);
        }
    }

    private Instant generateTime() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
    public String getSubject(String tokenJwt){
        try {
            var algoritmo = Algorithm.HMAC256(secret);
           return JWT.require(algoritmo)

                    .withIssuer("Api voil.api")

                    .build().verify(tokenJwt)
                   .getSubject();


        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token Jwt invalido o expirado");
        }
    }
}
