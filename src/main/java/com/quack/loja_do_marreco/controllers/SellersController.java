package com.quack.loja_do_marreco.controllers;

import com.quack.loja_do_marreco.controllers.dto.RegisterSellersDTO;
import com.quack.loja_do_marreco.services.SellersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(path = "/sellers")
@RequiredArgsConstructor
public class SellersController {


    private final SellersService sellersService;

    @PostMapping(path = "/register")
    public ResponseEntity<Void> registerSellers(@Valid @RequestBody RegisterSellersDTO dto) {

        var response = sellersService.registerSeller(dto);

        return ResponseEntity.created(URI.create("/" + response.getId())).build();
    }
}
