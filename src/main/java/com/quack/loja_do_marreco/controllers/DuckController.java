package com.quack.loja_do_marreco.controllers;

import com.quack.loja_do_marreco.controllers.dto.DuckRegisterDTO;
import com.quack.loja_do_marreco.services.DuckService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/quacks")
public class DuckController {


    private final DuckService duckService;

    @PostMapping(path = "/register")
    public ResponseEntity<Void> registerDuck(@RequestBody @Valid DuckRegisterDTO duckRegisterDTO){
        var duck = duckService.registerDuck(duckRegisterDTO);
        return ResponseEntity.created(URI.create("/quacks/" + duck.getId())).build();
    }
}
