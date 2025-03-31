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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/quacks")
@Tag(name = "Duck", description = "Cadastra, altera e busca um pato")
public class DuckController {


    private final DuckService duckService;
    private final ImportDucksService importDucksService;



    @Operation(summary = "Cadastra um novo pato e salva no banco de dados", method = "POST")
    @PostMapping(path = "/register")
    public ResponseEntity<Void> registerDuck(@RequestBody @Valid DuckRegisterDTO duckRegisterDTO){
        var duck = duckService.registerDuck(duckRegisterDTO);
        return ResponseEntity.created(URI.create("/quacks/" + duck.getId())).build();
    }


    @Operation(summary = "Busca todos os patos e retorna paginado", method = "GET")
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


    @Operation(summary = "Buscar patos com base na espécie e retorna paginado", method = "GET")
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


    @Operation(summary = "Busca todos os patos com base no status de disponibilidade", method = "GET")
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


    @Operation(summary = "Faz cadastro em lotes de patos com base em url de csv", method = "POST")
    @PostMapping(path = "/register/import")
    public ResponseEntity<Void> importDucksByCsv(@RequestBody ImportDucksDto dto) {

        CompletableFuture.runAsync(() -> {
            duckService.readDucksImportCsv(dto.csvPath());
        });

        return ResponseEntity.accepted().build();
    }


    @Operation(summary = "Mata um pato que está com status de disponível", method = "PUT")
    @PutMapping(path = "/{uuid}")
    public ResponseEntity<Void> killADuck(@PathVariable("uuid") UUID uuid) {

        var response = duckService.killADuck(uuid);

        return response ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
