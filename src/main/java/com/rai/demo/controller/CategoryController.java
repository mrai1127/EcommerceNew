package com.rai.demo.controller;

import com.rai.demo.model.Category;
import com.rai.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public String createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return "category created.";
    }

    @GetMapping("/list")
    public List<Category> listCategory(){
        return categoryService.listCategory();
    }
}
