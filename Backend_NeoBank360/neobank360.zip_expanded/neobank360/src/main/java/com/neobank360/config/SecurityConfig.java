package com.neobank360.config;

import com.neobank360.security.JwtFilter;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            // ✅ VERY IMPORTANT (ENABLE CORS HERE)
            .cors(cors -> {})

            .authorizeHttpRequests(auth -> auth
                // ✅ allow preflight requests (CRITICAL)
                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()

                .requestMatchers("/api/auth/**", "/api/otp/**").permitAll()
                .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/api/customer/**").hasAuthority("CUSTOMER")
                .anyRequest().authenticated()
            )

            // ✅ add JWT filter
            .addFilterBefore(jwtFilter,
                org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}