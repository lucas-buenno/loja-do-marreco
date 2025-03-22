package com.quack.loja_do_marreco.entities;

import com.quack.loja_do_marreco.entities.enums.DuckAvailability;
import com.quack.loja_do_marreco.entities.enums.DuckSpecie;
import com.quack.loja_do_marreco.entities.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "duck_db")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DuckEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "duck_specie")
    private DuckSpecie duckSpecie;

    @Column(name = "estimated_Date_of_birth")
    private LocalDate estimatedDateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "duck_gender")
    private Gender duckGender;

    @Column(name = "weight_in_kg")
    private Double weightInKg;

    @Column(name = "price")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability")
    private DuckAvailability availability;

    @Column(name = "entry_date")
    @CreationTimestamp
    private LocalDateTime entryDate;

    @Column(name = "sale_date")
    private LocalDateTime saleDate;

    @Column(name = "additional_details", length = 255)
    private String additionalDetails;
}
