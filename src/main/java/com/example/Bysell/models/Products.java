package com.example.Bysell.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "city")
    private String city;

    // Связь: один продукт имеет много изображений.
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "products")
    private List<Image> images = new ArrayList<>(); // Список изображений продукта.

    private Long previewImageId; // ID превью

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime dateOfCreated; // Дата создания продукта.

    // Установка даты создания перед сохранением продукта.
    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

    // Метод для добавления изображения к продукту.
    public void addImageToProduct(Image image) {
        image.setProducts(this); // Связываем изображение с продуктом.
        images.add(image); // Добавляем изображение в список.
    }
}
