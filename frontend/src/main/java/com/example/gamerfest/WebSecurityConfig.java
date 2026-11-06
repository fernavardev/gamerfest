package com.example.gamerfest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http,
        CustomAuthenticationProvider authenticationProvider) throws Exception {

        http
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/home",
                                "/login",
                                "/registro",
                                "/eventos",
                                "/css/**",
                                "/fonts/**",
                                "/images/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/login?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp.policyDirectives(
                                "default-src 'self'; "
                                        + "script-src 'self'; "
                                        + "style-src 'self'; "
                                        + "img-src 'self' data:; "
                                        + "font-src 'self'; "
                                        + "connect-src 'self'; "
                                        + "frame-ancestors 'none'; "
                                        + "form-action 'self'; "
                                        + "base-uri 'self'; "
                                        + "object-src 'none'"))
                        .frameOptions(frame -> frame.deny()));

        return http.build();
    }
}