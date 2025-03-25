package com.quack.loja_do_marreco.controllers.dto;

import com.quack.loja_do_marreco.entities.enums.DuckSpecie;
import com.quack.loja_do_marreco.entities.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DuckRegisterDTO(@NotNull(message = "Informe uma espécie de pato") DuckSpecie duckSpecie,
                              @NotNull(message = "Informe uma idade em meses") Integer ageInMonths,
                              @NotNull(message = "Informe um gênero") Gender duckGender,
                              @NotNull(message = "Informe um peso em kg") Double weightInKg,
                              String additionalDetails) {
}
