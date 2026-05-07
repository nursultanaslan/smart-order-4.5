package com.turkcell.order_service.application.command;

import com.turkcell.order_service.core.cqrs.CommandHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CancelOrderCommandHandler implements CommandHandler<CancelOrderCommand, UUID> {

    @Override
    public UUID handle(CancelOrderCommand command) {
        return null;
    }
}
