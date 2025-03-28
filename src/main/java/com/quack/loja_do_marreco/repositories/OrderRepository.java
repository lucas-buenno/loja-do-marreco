package com.quack.loja_do_marreco.repositories;

import com.quack.loja_do_marreco.entities.OrderEntity;
import com.quack.loja_do_marreco.entities.SellersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    List<OrderEntity> findBySeller(SellersEntity seller);
}
