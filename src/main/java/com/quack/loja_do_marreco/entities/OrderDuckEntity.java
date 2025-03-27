package com.quack.loja_do_marreco.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_duck_db")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDuckEntity {

    @EmbeddedId
    private OrderDuckId id;

    @Column(name = "total_price_duck")
    private BigDecimal totalPriceDuck;
}
