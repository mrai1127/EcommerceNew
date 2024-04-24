package com.rai.demo.service;

import com.rai.demo.model.Category;
import com.rai.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public void createCategory(Category category){
        categoryRepository.save(category);
    }

    public List<Category> listCategory(){
       return categoryRepository.findAll();
    }
}
