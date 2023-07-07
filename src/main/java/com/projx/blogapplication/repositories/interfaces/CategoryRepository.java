package com.projx.blogapplication.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projx.blogapplication.models.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
