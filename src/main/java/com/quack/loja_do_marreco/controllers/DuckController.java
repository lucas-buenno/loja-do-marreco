package com.quack.loja_do_marreco.controllers;

import com.quack.loja_do_marreco.controllers.dto.ApiResponse;
import com.quack.loja_do_marreco.controllers.dto.DuckRegisterDTO;
import com.quack.loja_do_marreco.controllers.dto.PaginationResponse;
import com.quack.loja_do_marreco.entities.DuckEntity;
import com.quack.loja_do_marreco.entities.enums.DuckSpecie;
import com.quack.loja_do_marreco.services.DuckService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping
    public ResponseEntity<ApiResponse<DuckEntity>> getRegisteredDucks(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "entryDate") String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "DESC") String sortOrder) {

        var response = duckService.getRegisteredDucks(pageNumber, pageSize, sortBy, sortOrder);

        return ResponseEntity.ok(new ApiResponse<>(response.getContent(),
                new PaginationResponse(pageNumber, pageSize, response.getTotalElements(), response.getTotalPages())));
    }

    @GetMapping(path = "/{duckSpecie}")
    public ResponseEntity<ApiResponse<DuckEntity>> getRegisteredDucksBySpecie(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "entryDate") String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "DESC") String sortOrder,
            @PathVariable("duckSpecie") DuckSpecie duckSpecie) {

        var response = duckService.getRegisteredDucksBySpecie(duckSpecie, pageNumber, pageSize, sortBy, sortOrder);

        return ResponseEntity.ok(new ApiResponse<>(response.getContent(),
                new PaginationResponse(pageNumber, pageSize, response.getTotalElements(), response.getTotalPages())));
    }
}
