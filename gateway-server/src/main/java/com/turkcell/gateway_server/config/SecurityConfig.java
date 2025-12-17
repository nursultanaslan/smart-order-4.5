package com.turkcell.gateway_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

//Her request gatewayden geçerken jwt dogrulamasına tabi tutulur.
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

        @Bean
        public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
                return http
                                .csrf(ServerHttpSecurity.CsrfSpec::disable) // csrf devredısı
                                // hangi isteklere izin verilsin? hangileri dogrulama gerektirsin?
                                //actuator dışındaki tüm istekler doğrulama gerektirsin.
                                .authorizeExchange(
                                                req -> req
                                                                .pathMatchers("/actuator/**").permitAll()
                                                                .anyExchange().authenticated())
                                //(access token validate)
                                // jwt filter yerine oauth2 kullanıyoruz (jwt validation)
                                .oauth2ResourceServer(oauth2 ->
                                        oauth2.jwt(Customizer.withDefaults()))
                                .build();
        }

}
