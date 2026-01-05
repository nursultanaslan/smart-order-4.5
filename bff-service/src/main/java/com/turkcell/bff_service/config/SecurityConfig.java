package com.turkcell.bff_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

//OAuthClient + Session
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

        @Bean
        public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
                return http
                                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                                .authorizeExchange(ex -> ex
                                                .pathMatchers(HttpMethod.GET,
                                                                "/api/v1/brands/**",
                                                                "/api/v1/categories/**",
                                                                "/api/v1/products/**")
                                                .permitAll()
                                                .anyExchange().authenticated())
                                // 1. Tarayıcı üzerinden giriş için (Session oluşturur)
                                .oauth2Login(Customizer.withDefaults())
                                .oauth2Client(Customizer.withDefaults())
                                .logout(l -> l.logoutUrl("/logout"))
                                .build();
        }
}
