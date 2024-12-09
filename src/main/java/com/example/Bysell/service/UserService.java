package com.example.Bysell.service;

import com.example.Bysell.models.User;
import com.example.Bysell.models.enums.Role;
import com.example.Bysell.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j // Логирование.
@RequiredArgsConstructor // Генерирует конструктор для final-полей.
public class UserService {
    private final UserRepository userRepository; // Репозиторий для работы с пользователями.
    private final PasswordEncoder passwordEncoder; // Кодировщик паролей.

    public boolean createUser(User user){
        String email = user.getEmail(); // Получает email пользователя.
        if (userRepository.findByEmail(email) != null) return false; // Проверяет, существует ли пользователь.
        user.setActive(true); // Активирует пользователя.
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Шифрует пароль.
        user.getRoles().add((Role.ROLE_USER)); // Устанавливает роль USER.
        log.info("Saving new User with email: {}", email); // Логирует сохранение.
        userRepository.save(user); // Сохраняет пользователя в базе данных.
        return true;
    }
}
