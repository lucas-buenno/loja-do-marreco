package com.quack.loja_do_marreco.controllers.dto;

import java.util.UUID;

public record SellerDto(UUID id,
                        String name,
                        String registration) {
}
