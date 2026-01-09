package com.turkcell.gateway_server.filter;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimitConfiguration {

    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> {
            //Sistemin onunde lb oldugundan gerçek kullanıcı ipsi X-Forwarded-For headerında gelir, önce buraya bakarız.
            String ipAddress = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");

            if (ipAddress == null && exchange.getRequest().getRemoteAddress() != null) {
                //eger header boşsa dogrudan gatewaye gelen makinanın ipsi alınır.
                ipAddress = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
            }
            //ikisi de yoksa unknown diyerek rate limitin çökmesi engellenir
            return Mono.just(ipAddress != null ? ipAddress : "unknown");
        };
    }
}
