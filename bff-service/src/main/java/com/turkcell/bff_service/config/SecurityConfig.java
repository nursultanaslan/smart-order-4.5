package com.turkcell.bff_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

//OAuthClient + Session
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

        @Order(Ordered.HIGHEST_PRECEDENCE)
        @Bean
        public SecurityWebFilterChain publicWebFilterChain(ServerHttpSecurity http) {
                return http
                        .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/api/v1/public/**"))
                        .authorizeExchange(ex -> ex.anyExchange().permitAll())
                        .csrf(ServerHttpSecurity.CsrfSpec::disable)
                        .build();
        }

        @Bean
        public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http)
        {
                return http
                        .csrf(ServerHttpSecurity.CsrfSpec::disable)
                        .authorizeExchange(ex -> ex.anyExchange().authenticated())
                        .oauth2Login(Customizer.withDefaults())
                        .oauth2Client(Customizer.withDefaults())
                        .build();
        }
}
