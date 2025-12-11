package com.turkcell.gateway_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

//Her request gatewayden geçerken jwt dogrulamasına tabi tutulur.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) //csrf devredısı
                //hangi isteklere izin verilsin? hangileri dogrulama gerektirsin?
                .authorizeHttpRequests(
                        req -> req
                                .requestMatchers("/actuator/**").permitAll()
                                .anyRequest().authenticated()
                )
                //jwt filter yerine oauth2 kullanıyoruz (jwt validation)
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(Customizer.withDefaults()))
                .build();
    }

}
