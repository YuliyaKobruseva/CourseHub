package com.example.coursehub.security.config;

import com.example.coursehub.security.user.Role;
import com.example.coursehub.security.user.User;
import com.example.coursehub.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                userRepository.save(User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .role(Role.ADMIN)
                        .build());
            }

            if (userRepository.findByUsername("student").isEmpty()) {
                userRepository.save(User.builder()
                        .username("student")
                        .password(passwordEncoder.encode("student123"))
                        .role(Role.STUDENT)
                        .build());
            }
        };
    }
}
