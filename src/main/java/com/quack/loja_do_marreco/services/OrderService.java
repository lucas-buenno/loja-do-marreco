package com.quack.loja_do_marreco.services;

import com.quack.loja_do_marreco.controllers.dto.CreateOrderDto;
import com.quack.loja_do_marreco.controllers.dto.OrderDuckDto;
import com.quack.loja_do_marreco.entities.*;
import com.quack.loja_do_marreco.entities.enums.DuckAvailability;
import com.quack.loja_do_marreco.exception.CustomerDoNotExistsException;
import com.quack.loja_do_marreco.exception.DuckDoNotExistsException;
import com.quack.loja_do_marreco.exception.DuckIsNotAvailable;
import com.quack.loja_do_marreco.exception.SellerDoNotExistsException;
import com.quack.loja_do_marreco.repositories.CustomersRepository;
import com.quack.loja_do_marreco.repositories.DuckRepository;
import com.quack.loja_do_marreco.repositories.OrderRepository;
import com.quack.loja_do_marreco.repositories.SellersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomersRepository customersRepository;
    private final SellersRepository sellersRepository;
    private final DuckRepository duckRepository;

    private static final double PERCENTAGE_DISCOUNT_AMOUNT = 0.20;
    
    @Transactional
    public OrderEntity createOrder(CreateOrderDto dto) {

        var orderEntity = new OrderEntity();

        var customer = getCustomer(dto.customerId());
        var seller = getSeller(dto.sellerId());
        var orderDucks = getOrderDucks(orderEntity, dto.listDucks());

        //Calculate Prices and Discount amount
        BigDecimal grossSaleValue = calculateGrossSaleValue(orderDucks);
        BigDecimal discountAmount = calculateDiscountAmount(grossSaleValue, customer);
        BigDecimal finalAmountWithDiscount = calculateFinalAmount(grossSaleValue, discountAmount);

        orderEntity.setCustomer(customer);
        orderEntity.setSeller(seller);
        orderEntity.setDucks(orderDucks);

        orderEntity.setGrossSaleValue(grossSaleValue);
        orderEntity.setDiscountAmount(discountAmount);
        orderEntity.setFinalAmountWithDiscount(finalAmountWithDiscount);

        return orderRepository.save(orderEntity);
    }

    private SellersEntity getSeller(UUID uuid) {
        log.info("Id do vendedor {}", uuid);
        return sellersRepository.findById(uuid).orElseThrow(
                () -> new SellerDoNotExistsException("O vendedor não está cadastrado"));
    }

    private BigDecimal calculateFinalAmount(BigDecimal grossSaleValue, BigDecimal discountAmount) {
        return grossSaleValue.subtract(discountAmount);
    }

    private BigDecimal calculateDiscountAmount(BigDecimal grossSaleValue, CustomersEntity customer) {
        if (!customer.isEligibleForDiscount()) {
            return BigDecimal.ZERO;
        }
        return grossSaleValue.multiply(BigDecimal.valueOf(PERCENTAGE_DISCOUNT_AMOUNT));
    }

    private BigDecimal calculateGrossSaleValue(List<OrderDuckEntity> orderDucks) {
        return orderDucks.stream()
                .map(OrderDuckEntity::getTotalPriceDuck)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }


    private List<OrderDuckEntity> getOrderDucks(OrderEntity orderEntity, List<OrderDuckDto> orderDuckDto) {
        return orderDuckDto.stream()
                .map(duckDto -> getOrderDuck(orderEntity, duckDto))
                .toList();
    }

    private OrderDuckEntity getOrderDuck(OrderEntity orderEntity, OrderDuckDto duckDto) {

        OrderDuckId id = new OrderDuckId();
        var duckEntity = validateDuck(duckDto);
        markDuckAsSold(duckEntity);

        id.setDuck(duckEntity);
        id.setOrder(orderEntity);

        return OrderDuckEntity.builder()
                .id(id)
                .totalPriceDuck(duckEntity.getPrice())
                .build();
    }


    private DuckEntity validateDuck(OrderDuckDto duckDto) {
        var duck = getDuck(duckDto);
        validateDuckAvailability(duck);
        return duck;
    }

    private static void markDuckAsSold(DuckEntity duck) {
        duck.setAvailability(DuckAvailability.SOLD);
    }


    private DuckEntity getDuck(OrderDuckDto duckDto) {
        var duck = duckRepository.findById(duckDto.duckId());

        if (duck.isEmpty()) {
            throw new DuckDoNotExistsException("O pato não está cadastrado");
        }
        return duck.get();
    }



    private void validateDuckAvailability(DuckEntity duck) {

         if (!duck.getAvailability().equals(DuckAvailability.AVAILABLE)) {
             log.info("DuckId {}, CustomerId {}", duck.getId(), LocalDateTime.now());
             throw new DuckIsNotAvailable("O pato não está disponível para venda");
         }
    }



    private CustomersEntity getCustomer(UUID uuid) {

        var customer = customersRepository.findById(uuid);

        if (customer.isEmpty()) {
            log.error("Não existe customer com o UUID informado {}", uuid);
            throw new CustomerDoNotExistsException("Não existe cliente cadastrado com o UUID informado");
        }

        return customer.get();
    }
}
