package com.example.itemfinderapplication.security;

import com.example.itemfinderapplication.security.jwt.JwtFilter;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Qeydiyyat ve Giris endpointlre her kes gire bilsin


                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/cities/**").permitAll()

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/uploads/**"
                        ).permitAll()

                        // Xüsusi GET endpointləri Mutleq TOKEN teleb edir
                        // BU setri umumi getden yuxarida olmalidir deye burada yazmisam
                        .requestMatchers(HttpMethod.GET, "/api/items/my-items").authenticated()

                        // Umumi get pointleri herkes elanlara baxa bilsin deye
                        // Bura bütün elanları listələyən (/api/items) endpointin aiddir
                        .requestMatchers(HttpMethod.GET, "/api/items").permitAll()

                        // Esya detallarini hamı gore biler - token lazim deyil
                        .requestMatchers(HttpMethod.GET, "/api/items/{id}").permitAll()

                        // Melumat yerlesdirmek (POST) ucun yoken mutleq lazimdir
                        .requestMatchers(HttpMethod.POST, "/api/items", "/api/items/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/cities/**").authenticated()

                        // Qalan butun sorgular token teleb edir
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

