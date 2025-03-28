package com.quack.loja_do_marreco.controllers.dto;

import java.util.UUID;

public record CustomerDto(UUID id,
                          String name,
                          String cpf,
                          Boolean eligibleForDiscount) {
}
