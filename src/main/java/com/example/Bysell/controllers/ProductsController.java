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

// позволяет обрабатывать HTTP-запросы и возвращать представления.
@Controller
//генерирует конструктор с параметрами для всех final полей
@RequiredArgsConstructor
public class ProductsController {
    private final ProductsService productsService;

    // передаёт список всех товаров на главную страницу
    @GetMapping("/")
    public String products(@RequestParam(name = "title", required = false) String title, Model model){
        model.addAttribute("products", productsService.listProducts(title));
        return "products";
    }

    // подробный просмотр для каждого товара
    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable long id, Model model){
        model.addAttribute("product", productsService.getProductById(id));
        return "product-info";
    }

    // добавляет новый товар
    @PostMapping("/product/create")
    public String createProduct(Products products){
        productsService.saveProducts(products);
        return "redirect:/";
    }

    // удаляет товар
    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productsService.deleteProduct(id);
        return "redirect:/";
    }
}
