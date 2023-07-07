package com.projx.blogapplication.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.projx.blogapplication.models.entities.Category;
import com.projx.blogapplication.models.payloads.AddCategoryRequest;
import com.projx.blogapplication.services.interfaces.CategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getCategories() {
        List<Category> categories = categoryService.get();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> saveCategory(@Valid @RequestBody AddCategoryRequest addCategoryRequest) {
//        String categoryId = categoryService.save(addCategoryRequest);
        return ResponseEntity.ok().build();
    }
}
