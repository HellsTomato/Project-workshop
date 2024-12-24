package com.example.Bysell.controllers;

import com.example.Bysell.models.User;
import com.example.Bysell.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) { // Вызывает сервис для создания нового пользователя.
            model.addAttribute("errorMessage", "Пользователь с email:" + user.getEmail() + "уже существует");
            return "registration"; // Перенаправляет на страницу логина.
        }
        return "redirect:/login";
    }

    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        return "user-info";
    }
}
