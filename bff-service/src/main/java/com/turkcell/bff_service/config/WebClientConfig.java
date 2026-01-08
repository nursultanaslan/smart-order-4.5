package com.turkcell.bff_service.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

//diger servicelere token taşır. (for gatewayRelayerController)
@Configuration
public class WebClientConfig {

    //webclientı loadbalanced hale getiriyorum ki discovery servis ile calısabilsin.
    @Bean
    @LoadBalanced
    WebClient.Builder loadBalancedWebClient() {
        return WebClient.builder();
    }

    //protected endpointler icin -> isteğe token ekler downstream servislere giderken
    @Bean
    WebClient authorizedWebClient(
                        @Qualifier("loadBalancedWebClient") WebClient.Builder builder, //yukarıdaki builderi okuduk
                        ReactiveClientRegistrationRepository client,  //yonlendirme icin metadataları alır.
                        ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {

            //her istekte bff'e gelen kullanıcının tokenini alıp gideceği microservisin headerına ekler.
            //Session'daki bilgiyi alır, Keycloak'tan JWT'yi bulur atacagı istegin header'ına ekler
            var oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(client, authorizedClientRepository);
            oauth.setDefaultClientRegistrationId("keycloak");
            oauth.setDefaultOAuth2AuthorizedClient(true);

            return builder.clone().filter(oauth).build();
    }

    //tokensiz public istekler icin
    //filtre uygulamaz istekte sessionid aramaz.
    @Bean
    WebClient publicWebClient(@Qualifier("loadBalancedWebClient") WebClient.Builder builder) {
        return builder.clone().build();
    }
}
