package com.quack.loja_do_marreco.controllers;

import com.quack.loja_do_marreco.controllers.dto.RegisterCustomerDto;
import com.quack.loja_do_marreco.entities.CustomersEntity;
import com.quack.loja_do_marreco.services.CustomersService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(path = "/customers")
@RequiredArgsConstructor
public class CustomersController {

    private final CustomersService customersService;

    @Operation(summary = "Cadastra um novo cliente e salva no banco de dados", method = "POST")
    @PostMapping(path = "/register")
    public ResponseEntity<Void> registerCustomer(@Valid @RequestBody RegisterCustomerDto dto) {

        var response = customersService.registerCustomer(dto);
        return ResponseEntity.created(URI.create("/" + response.getId())).build();
    }

    @Operation(summary = "Busca um cliente por Cpf", method = "GET")
    @GetMapping(path = "/{uuid}")
    public ResponseEntity<CustomersEntity> getByCpf(@PathVariable UUID id) {

        var customers = customersService.getById(id);

        return customers.isPresent() ?
                ResponseEntity.ok(customers.get()) :
                ResponseEntity.noContent().build();
    }
}
