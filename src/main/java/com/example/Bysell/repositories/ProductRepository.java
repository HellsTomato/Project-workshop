package com.example.Bysell.repositories;

import com.example.Bysell.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Long> {
    List<Products> findByTitle(String title);
}
