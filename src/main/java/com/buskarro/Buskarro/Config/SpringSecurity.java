package com.buskarro.Buskarro.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // disable CSRF
                .httpBasic(Customizer.withDefaults()) // enable Basic Auth
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/Register/**").permitAll() // open /Register
                        .anyRequest().authenticated() // everything else requires login
                );
        return http.build();
    }
}
