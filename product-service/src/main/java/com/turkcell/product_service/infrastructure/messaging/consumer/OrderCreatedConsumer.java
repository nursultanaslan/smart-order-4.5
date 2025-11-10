package com.turkcell.product_service.infrastructure.messaging.consumer;

import com.turkcell.product_service.infrastructure.messaging.contract.OrderCreatedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class OrderCreatedConsumer {
    //binding adında bir bean oluşturduk (orderCreated)
    //Topicten gelen mesajlar bu bean'e yönlenir
    //gelen eventle ne yapmak istediğimiz içerisinde tanımlanır
    @Bean
    public Consumer<OrderCreatedEvent> orderCreated() {
        return event -> {
            System.out.println("Yeni bir order create edildi.");
            System.out.println("Stok düş.");
            System.out.println(event.id());
        };
    }
}
