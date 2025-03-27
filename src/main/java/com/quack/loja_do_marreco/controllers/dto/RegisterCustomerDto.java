package com.quack.loja_do_marreco.controllers.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record RegisterCustomerDto(@NotNull String name,
                                  @NotNull @CPF(message = "É necessário informar o CPF") String cpf,
                                  Boolean eligibleForDiscount) {
}
