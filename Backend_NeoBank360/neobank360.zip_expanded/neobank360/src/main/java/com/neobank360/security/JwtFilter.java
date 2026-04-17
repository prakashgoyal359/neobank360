package com.neobank360.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                   HttpServletResponse res,
                                   FilterChain chain)
            throws ServletException, IOException {

        String path = req.getServletPath();

        // ✅ 1. SKIP JWT FOR PUBLIC APIs
        if (path.startsWith("/api/auth") || path.startsWith("/api/otp")) {
            chain.doFilter(req, res);
            return;
        }

        String header = req.getHeader("Authorization");

        // ✅ 2. CHECK TOKEN EXISTS
        if (header != null && header.startsWith("Bearer ")) {

            try {
                String token = header.substring(7);

                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);

                // ✅ 3. SET AUTHENTICATION
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of(new SimpleGrantedAuthority(role))
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                // ✅ 4. PREVENT SERVER CRASH (VERY IMPORTANT)
                System.out.println("JWT Error: " + e.getMessage());
            }
        }

        // ✅ 5. CONTINUE REQUEST
        chain.doFilter(req, res);
    }
}