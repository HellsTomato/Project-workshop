package com.example.Bysell.controllers;

import com.example.Bysell.models.Products;
import com.example.Bysell.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

// позволяет обрабатывать HTTP-запросы и возвращать представления.
@Controller
//генерирует конструктор с параметрами для всех final полей
@RequiredArgsConstructor
public class ProductsController {
    private final ProductsService productsService;

    // передаёт список всех товаров на главную страницу
    @GetMapping("/")
    public String products(@RequestParam(name = "title", required = false) String title, Principal principal, Model model){
        model.addAttribute("products", productsService.listProducts(title));
        model.addAttribute("user", productsService.getUserByPrincipal(principal));
        return "products";
    }

    // подробный просмотр для каждого товара
    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model) {
        // Получаем продукт по ID и добавляем в модель.
        Products product = productsService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages()); // Добавляем связанные изображения.
        return "product-info";
    }


    // добавляет новый товар
    @PostMapping("/product/create")
    public String createProduct(Products products,
                                @RequestParam("file1") MultipartFile file1,
                                @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3, Principal principal) throws IOException {
        productsService.saveProducts(principal, products, file1, file2, file3);
        return "redirect:/";
    }

    // удаляет товар
    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productsService.deleteProduct(id);
        return "redirect:/";
    }
}
