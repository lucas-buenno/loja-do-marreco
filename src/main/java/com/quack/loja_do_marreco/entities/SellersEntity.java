package com.quack.loja_do_marreco.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "sellers_db")
public class SellersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "seller_name")
    private String sellerName;

    @Column(name = "seller_cpf", unique = true)
    private String sellerCpf;

    @Column(name = "seller_registration", unique = true)
    private String sellerRegistration;

}
