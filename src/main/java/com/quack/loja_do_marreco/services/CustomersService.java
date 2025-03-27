package com.quack.loja_do_marreco.services;

import com.quack.loja_do_marreco.controllers.dto.RegisterCustomerDto;
import com.quack.loja_do_marreco.entities.CustomersEntity;
import com.quack.loja_do_marreco.exception.CpfAlreadyRegisteredException;
import com.quack.loja_do_marreco.repositories.CustomersRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomersService {

    private final CustomersRepository customersRepository;

    public CustomersEntity registerCustomer(RegisterCustomerDto dto) {

        verifyIfCpfIsAlreadyRegistered(dto.cpf());

        var customer = CustomersEntity.builder()
                .customerName(dto.name())
                .customerCpf(dto.cpf())
                .eligibleForDiscount(dto.eligibleForDiscount() != null && dto.eligibleForDiscount())
                .build();

        return customersRepository.save(customer);
    }

    private void verifyIfCpfIsAlreadyRegistered(String cpf) {

        var registered = customersRepository.existsByCustomerCpf(cpf);

        if (registered) {
            log.error("CPF já está cadastrado - CPF: {}", cpf);
            throw new CpfAlreadyRegisteredException("O CPF já está cadastrado");
        }
    }

    public Optional<CustomersEntity> getById(UUID uuid) {
        return customersRepository.findById(uuid);
    }
}
