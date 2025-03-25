package com.quack.loja_do_marreco.domain;

import com.opencsv.bean.CsvBindByName;
import com.quack.loja_do_marreco.entities.enums.DuckSpecie;
import com.quack.loja_do_marreco.entities.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CsvToImportDucks {

    @CsvBindByName(column = "duckSpecie")
    private DuckSpecie duckSpecie;

    @CsvBindByName(column = "ageInMonths")
    private Integer ageInMonths;

    @CsvBindByName(column = "duckGender")
    private Gender duckGender;

    @CsvBindByName(column = "weightInKg")
    private Double weightInKg;

    @CsvBindByName(column = "additionalDetails")
    private String additionalDetails;
}
