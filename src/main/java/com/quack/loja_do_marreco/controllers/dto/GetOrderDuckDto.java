package com.quack.loja_do_marreco.controllers.dto;

import com.quack.loja_do_marreco.entities.enums.DuckSpecie;
import com.quack.loja_do_marreco.entities.enums.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record GetOrderDuckDto(UUID duckId,
                              DuckSpecie duckSpecie,
                              LocalDate estimatedDateOfBirth,
                              Gender duckGender,
                              BigDecimal sellingPrice) {
}
