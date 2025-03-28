package com.quack.loja_do_marreco.controllers.dto;

import java.math.BigDecimal;

public record SellersRankingDto(SellerDto seller,
                                Integer quantitySales,
                                BigDecimal totalValueSold) {
}
