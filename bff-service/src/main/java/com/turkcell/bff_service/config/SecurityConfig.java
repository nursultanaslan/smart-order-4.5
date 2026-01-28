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
        public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http)
        {
                return http
                        .csrf(ServerHttpSecurity.CsrfSpec::disable)
                        .authorizeExchange(ex -> ex
                                .pathMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                                .pathMatchers("/login/**", "/oauth2/**", "/logout/**").permitAll()
                                .anyExchange().authenticated())
                        .oauth2Login(Customizer.withDefaults())
                        .oauth2Client(Customizer.withDefaults())
                        .build();
        }
}
