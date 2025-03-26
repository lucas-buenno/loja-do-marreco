package com.quack.loja_do_marreco.controllers;

import com.quack.loja_do_marreco.controllers.dto.ApiResponse;
import com.quack.loja_do_marreco.controllers.dto.PaginationResponse;
import com.quack.loja_do_marreco.controllers.dto.RegisterSellersDTO;
import com.quack.loja_do_marreco.entities.SellersEntity;
import com.quack.loja_do_marreco.services.SellersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<ApiResponse<SellersEntity>> getSellers(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortOrder", defaultValue = "DESC") String sortOrder,
            @RequestParam(name = "sortBy", defaultValue = "sellerName") String sortBy
    ) {

        var response = sellersService.getSellers(pageNumber, pageSize, sortOrder, sortBy);

        return ResponseEntity.ok(new ApiResponse<>(response.getContent(),
                new PaginationResponse(pageNumber, pageSize, response.getTotalElements(), response.getTotalPages())));
    }

    @GetMapping(path = "/{cpfOrSellerRegistration}")
    public ResponseEntity<SellersEntity> getSellerByCpfOrSellerRegistration(
            @PathVariable("cpfOrSellerRegistration") String cpfOrSellerRegistration) {

        var seller =  sellersService.getByCpfOrSellerRegistration(cpfOrSellerRegistration);

        return seller.isPresent() ? ResponseEntity.ok().body(seller.get())
                : ResponseEntity.noContent().build();

    }
}
