package com.quack.loja_do_marreco.controllers.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record GetOrderDto(UUID id,
                          CustomerDto customer,
                          List<GetOrderDuckDto> orderDucks,
                          BigDecimal grossSaleValue,
                          BigDecimal discountAmount,
                          BigDecimal finalAmountWithDiscount,
                          SellerDto seller,
                          LocalDateTime soldIn) {
}
