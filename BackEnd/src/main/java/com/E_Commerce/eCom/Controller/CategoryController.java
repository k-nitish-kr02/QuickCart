package com.E_Commerce.eCom.Controller;

import com.E_Commerce.eCom.Configurations.AppConstants;
import com.E_Commerce.eCom.Payload.CategoryPayload.CategoryDTO;
import com.E_Commerce.eCom.Payload.CategoryPayload.CategoryResponse;
import com.E_Commerce.eCom.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;


    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategory(
            @RequestParam(name = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize ,
            @RequestParam(name = "sortBy" ,defaultValue = AppConstants.SORT_CATEGORIES_BY,required = false) String sortBy,
            @RequestParam(name = "sortOrder" ,defaultValue = AppConstants.SORT_DIR,required = false) String sortOrder
    ){
        CategoryResponse response = categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/seller/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO ){
        categoryDTO = categoryService.createCategory(categoryDTO);
        return  new ResponseEntity<>(categoryDTO,HttpStatus.OK);
    }

    @PutMapping("/seller/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId){
        categoryDTO = categoryService.updateCategory(categoryDTO,categoryId);
        return new ResponseEntity<>(categoryDTO,HttpStatus.OK);
    }

    @DeleteMapping("/seller/categories/{categoryId}")
    public ResponseEntity<String> deleteById(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category Deleted.",HttpStatus.OK);
    }


}
