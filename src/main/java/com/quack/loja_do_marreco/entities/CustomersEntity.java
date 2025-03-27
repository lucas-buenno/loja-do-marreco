package com.quack.loja_do_marreco.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "customers_db")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_cpf", unique = true)
    private String customerCpf;

    @Column(name = "eligible_for_discount")
    private boolean eligibleForDiscount;
}
