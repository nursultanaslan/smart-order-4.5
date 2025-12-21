package com.turkcell.bff_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestAttributeHandler;

//OAuthClient + Session
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrf ->
                        csrf
                                //tokeni bir cookie icerisinde sakla.
                                .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
                                // webflux icin tokenin her istekte handle edilmesini sağla.c
                                .csrfTokenRequestHandler(new ServerCsrfTokenRequestAttributeHandler()))
                .authorizeExchange(ex -> ex.anyExchange().authenticated())  //requestMatchers
                .oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults())
                .logout(l -> l.logoutUrl("/logout"))
                .build();
    }
}
