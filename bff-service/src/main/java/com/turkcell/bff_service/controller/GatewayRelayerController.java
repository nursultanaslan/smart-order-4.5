package com.turkcell.bff_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
/**
 * Minimal proxy: herhangi bir /api/** ile başlayan isteği, lb://gateway'e  yonlendirir.
 * Access token WebClient OAuth2 filter tarafından otomatik eklenir.
 *
 * Frontend'den gelen istekleri al, gateway-server üzerinden uygun ms'e yönlendir
 */
@RestController
@RequestMapping("/api")
public class GatewayRelayerController {

    private final WebClient webClient;

    public GatewayRelayerController(WebClient webClient) {
        this.webClient = webClient;
    }

    @RequestMapping("/**")  //api önekinden sonra gelen her isteği yakalar.
    public Mono<ResponseEntity<byte[]>> relay(ServerWebExchange exchange,
                                              @RequestBody(required = false) Mono<byte[]> body) { //Gelen http isteği, bodysi olan istekler
        //Uri parçalayalım
        URI fullPath = exchange.getRequest().getURI();  //gelen istegin urisini al
        String downStreamPath = fullPath.getPath();     //gelen isteğin urisinden yolunu al
        String query = fullPath.getRawQuery();          //gelen isteğin urisinden varsa query parametrelerini al

        //query varsa yani null değilse isteğin yoluna ? ekle + queryleri ekle
        //query yoksa yani null ise sadece isteğin yolunu al
        String pathWithQuery = query != null ? downStreamPath + "?" + query : downStreamPath;

        //gateway-server'a göndereceği adresi kurdu.
        String fullRequestPath = "http://gateway-server" + pathWithQuery;


        //webClient ile isteği gönder.
        return webClient
                .method(exchange.getRequest().getMethod()) //istekte gelen method
                .uri(fullRequestPath)      //uri'sini gateway-server adresi ile deiştiriyor.
                //eğer body varsa ekliyor yoksa boş gönderiyor
                .body(body != null ? BodyInserters.fromPublisher(body, byte[].class) : BodyInserters.empty())
                .exchangeToMono(response -> response.toEntity(byte[].class)); //dönen cevabı cliente aynı türde gönderiyor.

    }
}
