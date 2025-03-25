package com.quack.loja_do_marreco.controllers;

import com.quack.loja_do_marreco.controllers.dto.ApiResponse;
import com.quack.loja_do_marreco.controllers.dto.DuckRegisterDTO;
import com.quack.loja_do_marreco.controllers.dto.ImportDucksDto;
import com.quack.loja_do_marreco.controllers.dto.PaginationResponse;
import com.quack.loja_do_marreco.entities.DuckEntity;
import com.quack.loja_do_marreco.entities.enums.DuckAvailability;
import com.quack.loja_do_marreco.entities.enums.DuckSpecie;
import com.quack.loja_do_marreco.services.DuckService;
import com.quack.loja_do_marreco.services.ImportDucksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/quacks")
public class DuckController {


    private final DuckService duckService;
    private final ImportDucksService importDucksService;


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

    @GetMapping(path = "/availability/{availability}")
    public ResponseEntity<ApiResponse<DuckEntity>> getByAvailability(
            @PathVariable("availability")DuckAvailability availability,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "entryDate") String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "DESC") String sortOrder) {


        var response = duckService.getByAvailability(availability, pageNumber, pageSize, sortBy, sortOrder);

        return ResponseEntity.ok(new ApiResponse<>(response.getContent(),
                new PaginationResponse(pageNumber, pageSize, response.getTotalElements(), response.getTotalPages())));
    }

    @PostMapping(path = "/register/import")
    public ResponseEntity<Void> importDucksByCsv(@RequestBody ImportDucksDto dto) {

        CompletableFuture.runAsync(() -> {
            duckService.readDucksImportCsv(dto.csvPath());
        });

        return ResponseEntity.accepted().build();
    }
}
