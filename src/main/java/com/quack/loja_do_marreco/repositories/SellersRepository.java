package com.quack.loja_do_marreco.repositories;

import com.quack.loja_do_marreco.entities.SellersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SellersRepository extends JpaRepository<SellersEntity, UUID> {

    boolean existsBySellerCpf(String cpf);

    @Query("SELECT s FROM SellersEntity s WHERE s.sellerCpf = :cpfOrSellerRegistration OR s.sellerRegistration = :cpfOrSellerRegistration")
    Optional<SellersEntity> findBySellerCpfOrSellerRegistration(@Param("cpfOrSellerRegistration") String cpfOrSellerRegistration);

}
