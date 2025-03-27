package com.quack.loja_do_marreco.controllers;

import com.quack.loja_do_marreco.controllers.dto.CreateOrderDto;
import com.quack.loja_do_marreco.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping(path = "/create-order")
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderDto dto) {

        var response = orderService.createOrder(dto);

        return ResponseEntity.created(URI.create("/" + response.getId())).build();
    }
}
