package com.quack.loja_do_marreco.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RegistrationNumberGenerator {


    public String generateRegistration() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('A', 'Z')
                .filteredBy(Character::isLetterOrDigit)
                .usingRandom(new SecureRandom()::nextInt)
                .get();

        var register = generator.generate(5);

        return "M-" + register;

    }

}
