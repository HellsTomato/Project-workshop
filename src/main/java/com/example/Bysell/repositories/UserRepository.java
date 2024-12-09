package com.example.Bysell.repositories;

import com.example.Bysell.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); // Метод для поиска пользователя по email.
}
