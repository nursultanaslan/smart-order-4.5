package com.turkcell.bff_service.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    WebClient.Builder loadBalancedWebClient() {
        return WebClient.builder();
    }

    @Bean
    WebClient webClient(@Qualifier("loadBalancedWebClient") WebClient.Builder builder,
                        // Ekstra oauth2 gereksinimleri:
                        ReactiveClientRegistrationRepository client,
                        ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {

            var oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(client, authorizedClientRepository);
            oauth.setDefaultClientRegistrationId("keycloak");
            oauth.setDefaultOAuth2AuthorizedClient(true);

            return builder.filter(oauth).build();
    }
}
