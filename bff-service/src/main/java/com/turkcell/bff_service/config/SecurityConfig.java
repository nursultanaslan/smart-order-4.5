package com.turkcell.bff_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;

//OAuthClient + Session
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

        @Bean
        SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http)
        {
                return http
                        .csrf((csrf) -> csrf.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()))
                        .anonymous(Customizer.withDefaults())
                        .authorizeExchange(
                                ex ->
                                        ex
                                                .pathMatchers("/","/public/**","/actuator/**").permitAll()
                                                .pathMatchers(HttpMethod.GET, "/api/v1/products/**", "/api/v1/brands/**", "/api/v1/brands/**").permitAll()
                                                .anyExchange().authenticated()
                        )
                        .oauth2Login(Customizer.withDefaults())
                        .oauth2Client(Customizer.withDefaults())
                        .logout(l -> l.logoutUrl("/"))
                        .build();
        }
}
