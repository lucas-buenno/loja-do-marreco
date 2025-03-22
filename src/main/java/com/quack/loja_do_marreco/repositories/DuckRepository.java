package com.quack.loja_do_marreco.repositories;

import com.quack.loja_do_marreco.entities.DuckEntity;
import com.quack.loja_do_marreco.entities.enums.DuckSpecie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DuckRepository extends JpaRepository<DuckEntity, UUID> {

    Page<DuckEntity> findByDuckSpecieLike(DuckSpecie duckSpecie, PageRequest pageRequest);
}
