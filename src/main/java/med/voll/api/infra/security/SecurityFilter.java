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

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService ;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJwt=recuperarToken(request);
        if(tokenJwt!=null){
            var subject= tokenService.getSubject(tokenJwt);
            var usuario=repository.findByLogin(subject);

            var authentication=new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request,response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var autorizationHeader=request.getHeader("Autorization");
        if(autorizationHeader!=null){
            return autorizationHeader.replace("Bearer","");

           // throw new RuntimeException("Token jwt no enviado en el encabezado de Authorization");
        }
        return null;



    }
}
