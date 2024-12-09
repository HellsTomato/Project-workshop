package com.example.Bysell.models;

import com.example.Bysell.models.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private boolean active;

    // Связь с таблицей Image.
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // Внешний ключ для таблицы изображений.
    @JoinColumn(name = "image_id")
    // Аватар пользователя.
    private Image avatar;

    @Column(name = "password", length = 1000)
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)  // Список ролей.
    @CollectionTable(name = "user_role", // Таблица для хранения ролей.
    joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING) // Хранение ролей в строковом формате.
    private Set<Role> roles = new HashSet<>(); // Набор ролей пользователя.

    private LocalDateTime dateOfCreated; // Дата создания пользователя.

    // Метод вызывается перед сохранением в базу.
    @PrePersist
    private void init(){
        dateOfCreated = LocalDateTime.now();
    }

    // security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
