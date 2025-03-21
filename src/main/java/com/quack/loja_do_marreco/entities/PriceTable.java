package com.quack.loja_do_marreco.entities;


import com.quack.loja_do_marreco.entities.enums.DuckSpecie;
import com.quack.loja_do_marreco.entities.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Entity
@Table(name = "price_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PriceTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "duck_specie")
    private DuckSpecie duckSpecie;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "puppy_price")
    private BigDecimal puppyPrice;
}
