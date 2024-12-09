package com.example.Bysell.service;
import com.example.Bysell.models.Image;
import com.example.Bysell.models.Products;
import com.example.Bysell.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service // Сервисный компонент Spring для бизнес-логики
@Slf4j // Добавляет поддержку логирования
@RequiredArgsConstructor // Конструктор с параметрами для всех final полей.
public class ProductsService {
    // Репозиторий для работы с сущностью Products.
    private final ProductRepository productRepository;

    // получение всего листа товара с фильтрацией по названию
    public List<Products> listProducts(String title) {
        if (title != null)
            return productRepository.findByTitle(title);
        return productRepository.findAll();}

    // Метод для сохранения продукта с несколькими изображениями.
    public void saveProducts(Products products, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        Image image1;
        Image image2;
        Image image3;

        // Проверяем, не пуст ли первый файл.
        if (file1.getSize() !=0){
            image1 = toImageEntity(file1); // Конвертируем файл в сущность Image.
            image1.setPreviewImage(true); // Превью файл
            products.addImageToProduct(image1); // Добавление к продукту
        }
        if (file2.getSize() !=0){
            image2 = toImageEntity(file2);
            products.addImageToProduct(image2);
        }
        if (file3.getSize() !=0){
            image3 = toImageEntity(file3);
            products.addImageToProduct(image3);
        }

        // логировка инфы о сохранении товара
        log.info("Saving new Product. Title: {}; Author: {}", products.getTitle(),products.getAuthor());
        Products productFromDb = productRepository.save(products); // сохранение продукта в бд
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId()); // установка превьюшки(image1)
        productRepository.save(products); // сохранение изменений в бд
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image(); // 1. Создаём новый объект Image.
        image.setName(file.getName()); // 2. Устанавливаем системное имя файла
        image.setOriginalFileName(file.getOriginalFilename()); // 3. Сохраняем оригинальное имя файла, которое видит пользователь.
        image.setContentType(file.getContentType()); // 4. Указываем тип файла
        image.setSize(file.getSize()); // 5. Сохраняем размер файла в байтах.
        image.setBytes(file.getBytes()); // 6. Сохраняем содержимое файла в виде массива байтов.
        return image; // 7. Возвращаем готовый объект Image.
    }

    // удаление продукта по его ID.
    public void deleteProduct(long id){
        productRepository.deleteById(id);
    }

    // поиск продукта по его ID
    public Products getProductById(long id){
        return productRepository.findById(id).orElse(null);
    }

}
