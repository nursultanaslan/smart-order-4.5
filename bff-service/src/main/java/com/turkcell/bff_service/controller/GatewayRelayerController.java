package com.turkcell.bff_service.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Minimal proxy: herhangi bir /api/** ile başlayan isteği, lb://gateway'e  yonlendirir.
 * Access token WebClient OAuth2 filter tarafından otomatik eklenir.
 * Frontend'den gelen istekleri al, gateway-server üzerinden uygun ms'e yönlendir
 */
@RestController
@RequestMapping("/api")
public class GatewayRelayerController {

    private final WebClient authorizedWebClient;
    private final WebClient publicWebClient;

    public GatewayRelayerController( @Qualifier("authorizedWebClient") WebClient authorizedWebClient,
                                     @Qualifier("publicWebClient") WebClient publicWebClient) {
        this.authorizedWebClient = authorizedWebClient;
        this.publicWebClient = publicWebClient;
    }

    @RequestMapping("/**")  //api önekinden sonra gelen her isteği yakalar.
    public Mono<ResponseEntity<byte[]>> relay(
            ServerWebExchange exchange,
            @RequestBody(required = false) Mono<byte[]> body) { //Gelen http isteği, bodysi olan istekler

        //frontendden gelen isteğin path kısmını alır. (domain ve port kısmını atar.)
        String path = exchange.getRequest().getURI().getPath();
        //url'deki varsa queryleri alır
        String query = exchange.getRequest().getURI().getRawQuery();

        //alınan path ve parametreyi birleştir.
        String pathWithQuery = query != null ? path + "?" + query : path;
        //yeni hedef adresi oluşturur.
        String fullRequestPath = "http://gateway-server" + pathWithQuery;

        // Path /public/ içeriyorsa publicWebClient, içermiyorsa authorized olanı kullan
        WebClient selectedClient = path.contains("/public/") ? publicWebClient : authorizedWebClient;

        //gelen isteği webClient aracılıgıyla gateway'e fırlatır.
        return selectedClient
                .method(exchange.getRequest().getMethod())
                .uri(fullRequestPath)
                .body(body != null ? BodyInserters.fromPublisher(body, byte[].class) : BodyInserters.empty())
                .exchangeToMono(response -> response.toEntity(byte[].class));
    }
}
