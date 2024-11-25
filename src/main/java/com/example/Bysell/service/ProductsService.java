package com.example.Bysell.service;

import com.example.Bysell.models.Products;
import com.example.Bysell.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductsService {
    private final ProductRepository productRepository;

    // получение всего листа товара
    public List<Products> listProducts(String title) {
        if (title != null) return productRepository.findByTitle(title);
        return productRepository.findAll();}

    // Увеличивает id и присваивает его новому добавленному товару
    public void saveProducts(Products product){
        log.info("Saving new{}", product);
        productRepository.save(product);
    }

    //удаляет товар из списка по id
    public void deleteProduct(long id){
        productRepository.deleteById(id);
    }

    //Находит и возвращает товар с заданным id из списка.(для подробного просмотра)
    public Products getProductById(long id){
        return productRepository.findById(id).orElse(null);
    }

}
