package com.turkcell.product_service.infrastructure.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) //csrf devre dışı bırakıldı
                .authorizeHttpRequests(req -> req.anyRequest().authenticated())  //tüm istekler dogrulama gerektirsin
                .oauth2ResourceServer(
                        oauth2 -> oauth2.jwt(Customizer.withDefaults()))  //jwt filter yerine oauth2 server kullan
                .build();
    }
}
