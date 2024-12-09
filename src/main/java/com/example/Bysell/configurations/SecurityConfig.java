package com.example.Bysell.configurations;

import com.example.Bysell.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Конфигурационный класс Spring Security
@EnableWebSecurity // Включаем поддержку безопасности
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    // @Bean: это способ сказать Spring, чтобы он создал объект и сделал его доступным для других частей приложения.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth // Настройка авторизации запросов.
                        .requestMatchers("/", "/product/**", "/images/**", "/registration").permitAll() // Публичный доступ.
                        .anyRequest().authenticated() // Остальные запросы требуют авторизации.
                )
                .formLogin(form -> form // Настройка формы авторизации.
                        .loginPage("/login") // Указание пользовательской страницы для входа.
                        .permitAll() // Доступ к странице логина без авторизации.
                )
                .logout(LogoutConfigurer::permitAll // Доступ к выходу из системы для всех.
                )
                .build(); // Завершение конфигурации.
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Настройка аутентификации.
    }

    // Создаём Bean для шифрования паролей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8); // Шифрование паролей с силой 8
    }
}
