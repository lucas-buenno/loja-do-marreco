package com.quack.loja_do_marreco.repositories;

import com.quack.loja_do_marreco.entities.DuckPriceTable;
import com.quack.loja_do_marreco.entities.enums.DuckSpecie;
import com.quack.loja_do_marreco.entities.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DuckPriceTableRepository extends JpaRepository<DuckPriceTable, Integer> {

    DuckPriceTable findByDuckSpecieAndGender(DuckSpecie duckSpecie, Gender gender);
}
