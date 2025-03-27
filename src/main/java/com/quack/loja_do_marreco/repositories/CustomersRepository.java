package com.quack.loja_do_marreco.repositories;

import com.quack.loja_do_marreco.entities.CustomersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomersRepository extends JpaRepository<CustomersEntity, UUID> {

    Boolean existsByCustomerCpf(String cpf);
}
