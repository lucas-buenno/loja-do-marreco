package com.quack.loja_do_marreco.controllers;

import com.quack.loja_do_marreco.controllers.dto.CreateOrderDto;
import com.quack.loja_do_marreco.controllers.dto.GetOrderDto;
import com.quack.loja_do_marreco.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/orders")
@Tag(name = "Order", description = "Cria um pedido e busca ele por uuid")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Cria um pedido e salva no banco de dados", method = "POST")
    @PostMapping(path = "/create-order")
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderDto dto) {

        var response = orderService.createOrder(dto);

        return ResponseEntity.created(URI.create("/" + response.getId())).build();
    }



    @Operation(summary = "Busca um pedido por UUID", method = "GET")
    @GetMapping(path = "/{id}")
    public ResponseEntity<GetOrderDto> getOrderById(@PathVariable("id") UUID id) {
        var response = orderService.getOrderById(id);
        return ResponseEntity.ok(response);
    }
}
