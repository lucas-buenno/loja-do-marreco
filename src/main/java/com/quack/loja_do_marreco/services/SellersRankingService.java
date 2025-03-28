package com.quack.loja_do_marreco.services;

import com.quack.loja_do_marreco.controllers.dto.SellerDto;
import com.quack.loja_do_marreco.controllers.dto.SellersRankingDto;
import com.quack.loja_do_marreco.entities.OrderEntity;
import com.quack.loja_do_marreco.entities.SellersEntity;
import com.quack.loja_do_marreco.repositories.OrderRepository;
import com.quack.loja_do_marreco.repositories.SellersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SellersRankingService {

    private final OrderRepository orderRepository;
    private final SellersRepository sellersRepository;





    public Page<SellersRankingDto> getSellersRanking(Integer pageNumber, Integer pageSize, String sortOrder, String sortBy) {

        var direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        var pageRequest = PageRequest.of(pageNumber, pageSize);

        List<SellersRankingDto> rankingList = new ArrayList<>();
        var listSellers = sellersRepository.findAll(pageRequest);


        listSellers.forEach(seller -> {
            var orders = orderRepository.findBySeller(seller);
            var sellerRanking = getSellerRanking(seller, orders);
            rankingList.add(sellerRanking);
        });

        //CRIA UMA ORDENAÇÃO COM BASE NOS PARAMETROS sortOrder e sortBy
        Comparator<SellersRankingDto> comparator = getSellersRankingDtoComparator(sortOrder, sortBy);
        rankingList.sort(comparator);

        return new PageImpl<>(rankingList, pageRequest, listSellers.getTotalPages());
    }

    private static Comparator<SellersRankingDto> getSellersRankingDtoComparator(String sortOrder, String sortBy) {
        Comparator<SellersRankingDto> comparator;
        if (sortBy.equalsIgnoreCase("totalValueSold")){
            comparator = Comparator.comparing(SellersRankingDto::totalValueSold);
        } else {
            comparator = Comparator.comparing(SellersRankingDto::quantitySales);
        }
        if (sortOrder.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

    private SellersRankingDto getSellerRanking(SellersEntity seller, List<OrderEntity> orders) {

        var totalValueSales = calculateTotalValue(orders);

        return new SellersRankingDto(
                new SellerDto(seller.getId(), seller.getSellerName(), seller.getSellerRegistration()),
                orders.size(),
                totalValueSales);
    }

    private BigDecimal calculateTotalValue(List<OrderEntity> orders) {
        return orders.stream().map(OrderEntity::getFinalAmountWithDiscount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
