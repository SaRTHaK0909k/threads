package com.projx.blogapplication.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.projx.blogapplication.models.entities.Category;
import com.projx.blogapplication.repositories.interfaces.CategoryRepository;
import com.projx.blogapplication.services.interfaces.CategoryService;

import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> get() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

}
