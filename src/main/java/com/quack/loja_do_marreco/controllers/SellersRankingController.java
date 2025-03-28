package com.quack.loja_do_marreco.controllers;

import com.quack.loja_do_marreco.controllers.dto.ApiResponse;
import com.quack.loja_do_marreco.controllers.dto.PaginationResponse;
import com.quack.loja_do_marreco.controllers.dto.SellersRankingDto;
import com.quack.loja_do_marreco.services.SellersRankingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/sellers-ranking")
public class SellersRankingController {


    private final SellersRankingService rankingSellersService;

    @Operation(summary = "Gera um ranking de vendedores com base em valor total vendido e quantidade de vendas", method = "GET")
    @GetMapping
    public ResponseEntity<ApiResponse<SellersRankingDto>> sellersRanking(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortOrder", defaultValue = "DESC") String sortOrder,
            @RequestParam(name = "sortBy", defaultValue = "totalValueSold") String sortBy
    ) {

        var response = rankingSellersService.getSellersRanking(pageNumber, pageSize, sortOrder, sortBy);

        return ResponseEntity.ok(new ApiResponse<>(response.getContent(),
                new PaginationResponse(response.getNumber(), response.getSize(), response.getTotalElements(), response.getTotalPages())));

    }

}
