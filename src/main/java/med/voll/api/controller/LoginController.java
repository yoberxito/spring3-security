package med.voll.api.controller;/*
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

import jakarta.validation.Valid;
import med.voll.api.domain.usuarios.DatosAutentication;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.infra.security.DatosTokenJwt;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager  manager;

    @PostMapping
    public ResponseEntity loginUser(@Valid @RequestBody DatosAutentication autentication){

        var token= new UsernamePasswordAuthenticationToken(autentication.login(),autentication.contrasena());

        var authenticated=manager.authenticate(token);
        var tokenJwt=tokenService.getAccesToken((Usuario) authenticated.getPrincipal());

        return  ResponseEntity.ok(new DatosTokenJwt(tokenJwt));
    };
}
