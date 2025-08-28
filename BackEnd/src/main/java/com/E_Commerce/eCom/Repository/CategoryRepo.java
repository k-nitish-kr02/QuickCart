package com.E_Commerce.eCom.Repository;

import com.E_Commerce.eCom.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    Optional<Category> findByCategoryName(String categoryName);
}
