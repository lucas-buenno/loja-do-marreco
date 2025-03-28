package com.quack.loja_do_marreco.controllers;

import com.quack.loja_do_marreco.controllers.dto.CreateOrderDto;
import com.quack.loja_do_marreco.controllers.dto.GetOrderDto;
import com.quack.loja_do_marreco.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

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


    @GetMapping(path = "/{id}")
    public ResponseEntity<GetOrderDto> getOrderById(@PathVariable("id") UUID id) {
        var response = orderService.getOrderById(id);
        return ResponseEntity.ok(response);
    }
}
