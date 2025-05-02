package com.example.passenger.config;

import com.example.passenger.repository.PassengerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.User;

@Configuration
public class AppConfig {

    @Bean
    public UserDetailsService userDetailsService(PassengerRepository repo) {
        return username -> repo.findByUsername(username)
                .map(p -> User.builder()
                        .username(p.getUsername())
                        .password(p.getPassword())
                        .roles("USER")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
