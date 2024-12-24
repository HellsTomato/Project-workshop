package com.example.Bysell.service;

import com.example.Bysell.models.User;
import com.example.Bysell.models.enums.Role;
import com.example.Bysell.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<User> list(){
        return userRepository.findAll();
    }

    public void banUser(Long id){
        User user = userRepository.findById(id).orElse(null);
        if (user != null){
            if(user.isActive()){
                user.setActive(false);
                log.info("Ban user with id = {}; email: {}", user.getId(), user.getEmail());
            } else {
                user.setActive(true);
                log.info("UnBan user with id = {}; email: {}", user.getId(), user.getEmail());
            }
        }
        userRepository.save(user);
    }

    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

}
