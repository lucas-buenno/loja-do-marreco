package com.quack.loja_do_marreco.controllers.dto;

import java.util.List;
import java.util.UUID;

public record CreateOrderDto(UUID customerId,
                             List<OrderDuckDto> listDucks,
                             UUID sellerId)  {
}
