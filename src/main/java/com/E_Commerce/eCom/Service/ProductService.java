package com.E_Commerce.eCom.Service;

import com.E_Commerce.eCom.Configurations.ObjectUtils;
import com.E_Commerce.eCom.ExceptionHandler.APIException;
import com.E_Commerce.eCom.ExceptionHandler.ResourceNotFoundException;
import com.E_Commerce.eCom.Model.Category;
import com.E_Commerce.eCom.Model.Product;
import com.E_Commerce.eCom.Payload.ProductPayload.ProductDTO;
import com.E_Commerce.eCom.Payload.ProductPayload.ProductResponse;
import com.E_Commerce.eCom.Repository.CategoryRepo;
import com.E_Commerce.eCom.Repository.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;
    private final CategoryRepo categoryRepo;

    @Autowired
    public ProductService(ProductRepo productRepo, ModelMapper modelMapper, CategoryRepo categoryRepo){
        this.modelMapper=modelMapper;
        this.productRepo=productRepo;
        this.categoryRepo = categoryRepo;
    }
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));

        productDTO.setProductImage("default img..");
        productDTO.setSpecialPrice(
                productDTO.getPrice() - (productDTO.getDiscount()*0.01* productDTO.getPrice())
        );

        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);

        return modelMapper.map(productRepo.save(product),ProductDTO.class);
    }




    private static Pageable getPageDetails(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of(pageNumber, pageSize,sortByAndOrder);
    }

    private ProductResponse getProductResponse(Page<Product> page) {
        List<Product> products = page.getContent();

        if(products.isEmpty()) throw new APIException("No products found..");

        List<ProductDTO> productDTOs = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();


        return ProductResponse.builder()
                .content(productDTOs)
                .pageSize(page.getSize())
                .pageNumber(page.getNumber())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .lastPage(page.isLast())
                .build();
    }

    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageDetails = getPageDetails(pageNumber, pageSize, sortBy, sortOrder);
        Page<Product> page = productRepo.findAll(pageDetails);

        return getProductResponse(page);
    }

    public ProductResponse getAllProducts(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageDetails = getPageDetails(pageNumber, pageSize, sortBy, sortOrder);

        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
        Page<Product> page = productRepo.findByCategory(category,pageDetails);

        return getProductResponse(page);
    }


    public ProductResponse getAllProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageDetails = getPageDetails(pageNumber,pageSize,sortBy,sortOrder);

        Page<Product> page = productRepo.findByProductNameLikeIgnoreCase('%'+keyword+'%',pageDetails);

        return getProductResponse(page);
    }

    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));

        ObjectUtils.setIfNotNull(productDTO.getCategoryId(),
                value -> { Category category = categoryRepo.findById(productDTO.getCategoryId())
                        .orElseThrow(
                                ()-> new ResourceNotFoundException("Category","categoryId",productDTO.getCategoryId()));
                    product.setCategory(category);
                });

//        ObjectUtils.setIfNotNull(productDTO.getProductId(),value -> product.setProductId(value));
        ObjectUtils.setIfNotNull(productDTO.getProductId(), product::setProductId);
        ObjectUtils.setIfNotNull(productDTO.getProductName(),product::setProductName);
        ObjectUtils.setIfNotNull(productDTO.getProductImage(),product::setProductImage);
        ObjectUtils.setIfNotNull(productDTO.getDescription(),product::setDescription);
        ObjectUtils.setIfNotNull(productDTO.getQuantity(),product::setQuantity);
        ObjectUtils.setIfNotNull(productDTO.getPrice(),product::setPrice);
        ObjectUtils.setIfNotNull(productDTO.getDiscount(),product::setDiscount);



        if(productDTO.getDiscount() != null){
            product.setSpecialPrice(
                    productDTO.getPrice() - (productDTO.getDiscount()*0.01* productDTO.getPrice())
            );
        } else {
            if(productDTO.getSpecialPrice() == null) product.setSpecialPrice(productDTO.getPrice());
            else {
                double discount = (productDTO.getPrice() - productDTO.getSpecialPrice())*100/productDTO.getPrice();
                product.setDiscount(discount);
                product.setSpecialPrice(productDTO.getSpecialPrice());
            }
        }

        Product savedProduct = productRepo.save(product);
        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));
        productRepo.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }
}
