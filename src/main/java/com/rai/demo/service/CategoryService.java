package com.rai.demo.service;

import com.rai.demo.model.Category;
import com.rai.demo.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public void createCategory(Category category){
        categoryRepository.save(category);

    }
}
