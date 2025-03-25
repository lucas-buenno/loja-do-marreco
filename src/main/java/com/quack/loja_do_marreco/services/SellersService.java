package com.quack.loja_do_marreco.services;

import com.quack.loja_do_marreco.controllers.dto.RegisterSellersDTO;
import com.quack.loja_do_marreco.entities.SellersEntity;
import com.quack.loja_do_marreco.exception.CpfAlreadyRegisteredException;
import com.quack.loja_do_marreco.repositories.SellersRepository;
import com.quack.loja_do_marreco.utils.RegistrationNumberGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellersService {

    private final SellersRepository sellersRepository;

    private final RegistrationNumberGenerator registrationNumberGenerator;

    public SellersEntity registerSeller(RegisterSellersDTO dto) {

        //Valida se o CPF já está cadastrado e lança uma exception
        verifyIfCpfIsAlreadyRegistered(dto.cpf());

        //Cria um número de matrícula único para o vendedor
        var sellersRegistration = registrationNumberGenerator.generateRegistration();


        var seller = SellersEntity.builder()
                .sellerCpf(dto.cpf())
                .sellerName(dto.name())
                .sellerRegistration(sellersRegistration)
                .build();

        return sellersRepository.save(seller);
    }


    private void verifyIfCpfIsAlreadyRegistered(String cpf) {

        var isRegistered = sellersRepository.existsBySellerCpf(cpf);

        if (isRegistered) {
            log.error("CPF já está cadastrado - CPF: {}", cpf);
            throw new CpfAlreadyRegisteredException("O CPF já está cadastradado.");
        }
    }
}
