package com.turkcell.product_service.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity //resource-level security
@EnableMethodSecurity //method-level security
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req
                                .requestMatchers(HttpMethod.GET, "/api/v1/public").permitAll()
                                //prodda swagger kapat!
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui.html"
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                //jwt doğrula ve authority'i çıkar
                .oauth2ResourceServer(oauth2 ->
                        oauth2
                                .jwt(jwtConfigurer -> jwtConfigurer
                                        .jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        //gelen jwt'yi extractAuthorities methodu ile authority listesini üret.
        converter.setJwtGrantedAuthoritiesConverter(this::extractAuthorities);
        return converter;
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {

        List<GrantedAuthority> authorities = new ArrayList<>();

        //jwt claimlerini al.
        var claims = jwt.getClaims();
        //realm_access.roles oku.
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");

        if (realmAccess != null) {
            //rolleri authority'e çevir (GrantedAuthority)
            //Spring security artık bu rolleri tanır.
            List<String> roles = (List<String>) realmAccess.get("roles");

            roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        }

        return authorities;
    }
}
