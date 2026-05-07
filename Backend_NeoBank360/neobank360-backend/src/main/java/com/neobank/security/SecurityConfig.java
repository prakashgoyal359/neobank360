package com.neobank.security;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filter(HttpSecurity http, JwtFilter filter) throws Exception {

        http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/api/auth/**","/api/otp/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}