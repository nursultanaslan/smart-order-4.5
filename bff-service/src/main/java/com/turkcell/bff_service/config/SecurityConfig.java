package com.turkcell.bff_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
        @Order(1)
        public SecurityWebFilterChain publicWebFilterChain(ServerHttpSecurity http)
        {
                return http
                        .csrf(ServerHttpSecurity.CsrfSpec::disable)
                        .authorizeExchange(ex -> ex
                                // Public yolları açıkça belirtiyoruz
                                .pathMatchers(HttpMethod.GET, "/api/v1/public/**").permitAll()
                        )
                        .build();
        }

        @Bean
        @Order(2)
        public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http)
        {
                return http
                        .csrf(ServerHttpSecurity.CsrfSpec::disable)
                        .authorizeExchange(e -> e.anyExchange().authenticated())
                        .oauth2Login(Customizer.withDefaults())
                        .oauth2Client(Customizer.withDefaults())
                        .logout(l -> l.logoutUrl("/"))
                        .build();
        }
}
