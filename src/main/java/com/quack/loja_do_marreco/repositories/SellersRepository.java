package com.quack.loja_do_marreco.repositories;

import com.quack.loja_do_marreco.entities.SellersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SellersRepository extends JpaRepository<SellersEntity, UUID> {

    boolean existsBySellerCpf(String cpf);
}
