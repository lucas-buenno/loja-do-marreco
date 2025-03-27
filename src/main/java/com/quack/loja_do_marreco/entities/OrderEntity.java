package com.quack.loja_do_marreco.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_tb")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @OneToMany(mappedBy = "id.order", cascade = CascadeType.ALL) //CRIAR ORDEM ITEM ENTITY
    private List<OrderDuckEntity> ducks;

    @Column(name = "grossSaleValue")
    private BigDecimal grossSaleValue;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @Column(name = "final_amount_with_discount")
    private BigDecimal finalAmountWithDiscount;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomersEntity customer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private SellersEntity seller;

    @CreationTimestamp
    @Column(name = "sold_in")
    private LocalDateTime soldIn;

}
