package com.turkcell.product_service.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    @GetMapping
    public String get(){
        return "Products";
    }

    @GetMapping("{id}")
    public String getById(@PathVariable Integer id){
        return "Product: " + id;
    }

    @PostMapping
    public String create(){
        return "Product created successfully";
    }

    @PutMapping
    public String update(){
        return "Product updated successfully";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id){
        return "Product deleted successfully";
    }

}
