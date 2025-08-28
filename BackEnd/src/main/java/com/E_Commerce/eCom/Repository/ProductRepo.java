package com.E_Commerce.eCom.Repository;

import com.E_Commerce.eCom.Model.Category;
import com.E_Commerce.eCom.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Page<Product> findByCategory(Category category,Pageable pageDetails);

    Page<Product> findByProductNameLikeIgnoreCase(String productName, Pageable pagDetails);
}
