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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @Autowired
    private SecurityFilter securityFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf( t ->t.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(t->{
                    t.requestMatchers(HttpMethod.POST,"/login").permitAll();
                    t.anyRequest().authenticated();

                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
