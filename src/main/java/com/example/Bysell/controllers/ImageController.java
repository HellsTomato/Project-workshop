package com.example.Bysell.controllers;

import com.example.Bysell.models.Image; // Импортируем сущность Image.
import com.example.Bysell.repositories.ImageRepository;  // Репозиторий для работы с изображениями.
import lombok.RequiredArgsConstructor; // Импорт аннотации для автоматической генерации конструктора.
import org.springframework.core.io.InputStreamResource; // Используется для возврата байтовых данных.
import org.springframework.http.MediaType; // Класс для указания типа контента (MIME-типа).
import org.springframework.http.ResponseEntity; // Класс для формирования HTTP-ответа.
import org.springframework.web.bind.annotation.GetMapping; // Указывает маршруты для HTTP GET запросов.
import org.springframework.web.bind.annotation.PathVariable; // Позволяет получать переменные из URL.
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageRepository imageRepository;

    // Метод для получения изображения по ID
    @GetMapping("/images/{id}")
    // Получаем изображение из базы данных по ID.
    public ResponseEntity<?> getImageById(@PathVariable long id) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image == null) { // ошибка 404
            return ResponseEntity.notFound().build(); // Если изображение не найдено, вернуть 404
        }
        // Возвращаем изображение с указанием MIME-типа, размера и байтов.
        return ResponseEntity.ok()
                .header("fileName", image.getOriginalFileName()) // Устанавливаем оригинальное имя файла в заголовок
                .contentType(MediaType.valueOf(image.getContentType())) // Устанавливаем MIME-тип контента
                .contentLength(image.getSize()) // Указываем размер контента
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes()))); // Возвращаем байты изображения
    }
}

