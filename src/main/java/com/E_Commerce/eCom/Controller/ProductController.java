package com.E_Commerce.eCom.Controller;

import com.E_Commerce.eCom.Configurations.AppConstants;
import com.E_Commerce.eCom.Payload.ProductPayload.ProductDTO;
import com.E_Commerce.eCom.Payload.ProductPayload.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.E_Commerce.eCom.Service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "pageNumber" ,defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
            @RequestParam(name = "pageSize" ,defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam(name = "sortBy" ,defaultValue = AppConstants.SORT_PRODUCTS_BY , required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR , required = false) String sortOrder
    ){
        ProductResponse response = productService.getAllProducts(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/products/public/categories/{categoryId}")
    public ResponseEntity<ProductResponse> getProductsByCategories(
            @PathVariable Long categoryId,
            @RequestParam(name = "pageNumber" ,defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
            @RequestParam(name = "pageSize" ,defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam(name = "sortBy" ,defaultValue = AppConstants.SORT_PRODUCTS_BY , required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR , required = false) String sortOrder
    ){
        ProductResponse response = productService.getAllProducts(categoryId,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(response,HttpStatus.FOUND);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(
            @PathVariable String keyword,
            @RequestParam(name = "pageNumber" ,defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
            @RequestParam(name = "pageSize" ,defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam(name = "sortBy" ,defaultValue = AppConstants.SORT_PRODUCTS_BY , required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR , required = false) String sortOrder
    ){
        ProductResponse response = productService.getAllProductsByKeyword(keyword,pageNumber,pageSize,sortBy,sortOrder);
        return  new ResponseEntity<>(response,HttpStatus.FOUND);

    }

    @PostMapping("/seller/product/categories/{categoryId}")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO,@PathVariable Long categoryId){
        ProductDTO productDTO1 = productService.addProduct(productDTO,categoryId);
        return new ResponseEntity<>(productDTO1, HttpStatus.CREATED);
    }

    @PutMapping("/seller/product/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO,@PathVariable Long productId){
        ProductDTO productDTO1 = productService.updateProduct(productDTO,productId);
        return  new ResponseEntity<>(productDTO1,HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/seller/product/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO productDTO = productService.deleteProduct(productId);
        return  new ResponseEntity<>(productDTO,HttpStatus.OK);
    }
}
