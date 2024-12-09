package com.example.Bysell.controllers;

import com.example.Bysell.models.User;
import com.example.Bysell.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // Маршрут для страницы логина.
    @GetMapping("/login")
    public String login() {
        return "login"; // Возвращает шаблон login.ftlh.
    }

    // Маршрут для страницы регистрации.
    @GetMapping("/registration")
    public String registration() {
        return "registration"; // Возвращает шаблон registration.ftlh.
    }

    // Обрабатывает POST-запрос на регистрацию.
    @PostMapping("/registration")
    public String createUser(User user) {
        userService.createUser(user); // Вызывает сервис для создания нового пользователя.
        return "redirect:/login"; // Перенаправляет на страницу логина.
    }

    // Маршрут для защищённой страницы.
    @GetMapping("/hello")
    public String securityUrl() {
        return "hello";
    }
}
