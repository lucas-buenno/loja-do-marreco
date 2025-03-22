package com.quack.loja_do_marreco.services;

import com.quack.loja_do_marreco.controllers.dto.DuckRegisterDTO;
import com.quack.loja_do_marreco.entities.DuckEntity;
import com.quack.loja_do_marreco.entities.enums.DuckAvailability;
import com.quack.loja_do_marreco.exception.AdvancedAgeException;
import com.quack.loja_do_marreco.exception.MaxWeightAllowedException;
import com.quack.loja_do_marreco.repositories.DuckRepository;
import com.quack.loja_do_marreco.repositories.DuckPriceTableRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.IllegalFormatCodePointException;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class DuckService {


    private static final Integer DUCK_PUBERTY_MONTHS = 5;
    private static final Integer MAXIMUM_AGE_ALLOWED = 120;
    private static final Integer MAXIMUM_KG_ALLOWED = 5;


    private final DuckRepository duckRepository;
    private final DuckPriceTableRepository duckPriceTableRepository;


    @Transactional
    public DuckEntity registerDuck(DuckRegisterDTO dto) {

        //validate dto
        validateRequestDto(dto);

        BigDecimal duckPrice = calculateDuckPrice(dto);

        //calculate age
        var duckDateBirth = calculateDateBirth(dto.ageInMonths());

        var duck = DuckEntity.builder()
                .duckSpecie(dto.duckSpecie())
                .duckGender(dto.duckGender())
                .estimatedDateOfBirth(duckDateBirth)
                .weightInKg(dto.weightInKg())
                .price(duckPrice)
                .availability(DuckAvailability.AVAILABLE)
                .additionalDetails(
                        isNull(dto.additionalDetails()) || dto.additionalDetails().isBlank()
                        ? "Sem detalhes adicionais"
                        : dto.additionalDetails())
                .build();

        return duckRepository.save(duck);
    }

    private static void validateRequestDto(DuckRegisterDTO dto) {
        if (dto.ageInMonths() > MAXIMUM_AGE_ALLOWED) {
            throw new AdvancedAgeException("A idade máxima permitida é " + MAXIMUM_AGE_ALLOWED + " meses");
        }

        if (dto.weightInKg() > MAXIMUM_KG_ALLOWED) {
            throw new MaxWeightAllowedException("O peso máximo permitido é " + MAXIMUM_KG_ALLOWED + "kg");
        }
    }

    private LocalDate calculateDateBirth(Integer months) {
        var currentDate = LocalDate.now();
        return currentDate.minusMonths(months);
    }


    private BigDecimal calculateDuckPrice(DuckRegisterDTO dto) {

        var duckValuesByPriceTable = duckPriceTableRepository.findByDuckSpecieAndGender(dto.duckSpecie(), dto.duckGender());

        if (dto.ageInMonths() <= DUCK_PUBERTY_MONTHS) {
            return duckValuesByPriceTable.getPuppyPrice();
        }

        return duckValuesByPriceTable.getPrice();
    }
}
