package com.rrss2024.RRSS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

import com.rrss2024.RRSS.component.CustomAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfig(AuthenticationProvider authenticationProvider, CustomAuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationProvider = authenticationProvider;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Postman'den request atabilmek için CSRF disable edildi. (CSRF token lazım sanırım.)
                .authorizeHttpRequests(auth -> {
                    // Burası endpointler için authorizationu belirlediğimiz yer ve sonrasında güncelleyebiliriz.
                    auth.requestMatchers("/", "/auth/signup", "/auth/login").permitAll();
                    auth.anyRequest().authenticated(); // Request atarken sorun çıkarabilir o yüzden inceleme gerekli.
                })
                // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) Token bazlı doğrulama (şu an stateful) yapılacağı zaman kullanılacak.
                .oauth2Login(withDefaults()) // Standart OAuth login.
                .formLogin(withDefaults()) // Bunu Frontend gelince kaldırıcaz diye düşünüyorum.
                .httpBasic(x -> x.authenticationEntryPoint(authenticationEntryPoint)) 
                .authenticationProvider(authenticationProvider) // Custom provider.
                .build();
    }

}