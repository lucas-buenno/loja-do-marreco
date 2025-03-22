package com.quack.loja_do_marreco.repositories;

import com.quack.loja_do_marreco.entities.DuckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DuckRepository extends JpaRepository<DuckEntity, UUID> {


}
