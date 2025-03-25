package com.quack.loja_do_marreco.controllers.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record RegisterSellersDTO(@NotNull(message = "Informe um nome") String name,
                                 @CPF(message = "O CPF precisa ser válido") @NotNull(message = "Informe um CPF") String cpf) {
}
