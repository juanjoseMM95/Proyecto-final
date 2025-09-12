package com.arka.arkajjmunozm.application.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.arka.arkajjmunozm.domain.model.Category;
import com.arka.arkajjmunozm.domain.port.in.ICategoryService;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.CategoryEntity;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> allCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToDomain)
                .toList();
    }

    @Override
    public Category newCategory(Category category) {
        CategoryEntity entity = mapToEntity(category);
        CategoryEntity savedEntity = categoryRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    @Override
    public Category getCategory(int id) {
        return categoryRepository.findById(id)
                .map(this::mapToDomain)
                .orElse(null);
    }
    @Override
    public Category updateCategory(int id, Category category) {
        return categoryRepository.findById(id)
                .map(existingEntity -> {
                    category.setId(id);
                    CategoryEntity entityToUpdate = mapToEntity(category);
                    CategoryEntity updatedEntity = categoryRepository.save(entityToUpdate);
                    return mapToDomain(updatedEntity);
                })
                .orElse(null);
    }

    @Override
    public boolean deleteCategory(int id) {
        return categoryRepository.findById(id)
                .map(entity -> {
                    categoryRepository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }

    private Category mapToDomain(CategoryEntity entity) {
        return new Category(
                entity.getId(),
                entity.getType()
        );
    }

    private CategoryEntity mapToEntity(Category category) {
        return new CategoryEntity(
                category.getId(),
                category.getType(),null
        );
    }
}