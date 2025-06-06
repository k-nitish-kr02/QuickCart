package com.E_Commerce.eCom.Service;

import com.E_Commerce.eCom.ExceptionHandler.ResourceNotFoundException;
import com.E_Commerce.eCom.Payload.CategoryPayload.CategoryDTO;
import com.E_Commerce.eCom.Payload.CategoryPayload.CategoryResponse;
import com.E_Commerce.eCom.Repository.CategoryRepo;
import com.E_Commerce.eCom.ExceptionHandler.APIException;
import com.E_Commerce.eCom.Model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {


    private final CategoryRepo repo;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryService(CategoryRepo repo, ModelMapper modelMapper){
        this.repo = repo;
        this.modelMapper = modelMapper;
    }

    public CategoryResponse getAllCategories(Integer pageNumber , Integer pageSize,String sortBy , String sortOrder){

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Category> page = repo.findAll(pageDetails);
        List<Category> categories = page.getContent();
        if(categories.isEmpty()) throw new APIException("No Category created or present.");

        List<CategoryDTO> response = categories.stream()
                .map(category -> modelMapper.map(category,CategoryDTO.class)).toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(response);
        categoryResponse.setPageNumber(page.getNumber());
        categoryResponse.setLastPage(page.isLast());
        categoryResponse.setPageSize(page.getSize());
        categoryResponse.setTotalPages(page.getTotalPages());
        categoryResponse.setTotalElements(page.getTotalElements());
        return categoryResponse;
    }
    public CategoryDTO createCategory(CategoryDTO categoryDTO){
        Optional<Category> cat = repo.findByCategoryName(categoryDTO.getCategoryName());
        if(cat.isPresent()) throw  new APIException("Category name already exists.");
        Category savedCategory = repo.save(modelMapper.map(categoryDTO,Category.class));
        return modelMapper.map(savedCategory,CategoryDTO.class);

    }

    public void deleteCategory(Long categoryId){
        Category catOpt = repo.findById(categoryId).orElseThrow( () -> new ResourceNotFoundException("Category","categoryId",categoryId));
        repo.delete(catOpt);
    }

    public CategoryDTO updateCategory(CategoryDTO categoryDTO ,Long categoryId){
        Category catOpt = repo.findById(categoryId).orElseThrow( () -> new ResourceNotFoundException("Category","categoryId",categoryId));
        catOpt.setCategoryName(categoryDTO.getCategoryName());
        repo.save(catOpt);
        return modelMapper.map(catOpt,CategoryDTO.class);
    }
}
