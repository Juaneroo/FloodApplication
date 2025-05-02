package com.flood_web;

import com.flood_web.data.entity.AdministratorsEntity;
import com.flood_web.data.repository.AdministratorsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DefaultUserConfig {

    @Bean
    public CommandLineRunner initDefaultUser(AdministratorsRepository administratorsRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (administratorsRepository.findByCedula("000").isEmpty()) {
                administratorsRepository.save(
                        AdministratorsEntity.builder()
                                .withEntity("admin")
                                .withActive(true)
                                .withCedula("000")
                                .withName("super admin")
                                .withPassword(passwordEncoder.encode("admin"))
                                .withTelephone("+0000000000")
                                .withRole("ADMIN")
                                .build()
                );
            }
        };
    }
}