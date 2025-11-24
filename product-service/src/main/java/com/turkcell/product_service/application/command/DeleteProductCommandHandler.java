package com.turkcell.product_service.application.command;

import com.turkcell.product_service.application.dto.DeletedProductResponse;
import com.turkcell.product_service.core.cqrs.CommandHandler;

public class DeleteProductCommandHandler implements CommandHandler<DeleteProductCommand, DeletedProductResponse> {

    @Override
    public DeletedProductResponse handle(DeleteProductCommand command) {
        return null;
    }
}
