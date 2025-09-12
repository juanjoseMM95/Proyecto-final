package com.arka.arkajjmunozm.domain.port.in;

import com.arka.arkajjmunozm.domain.model.Category;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.CategoryEntity;
import java.util.List;

public interface ICategoryService {
    List<Category> allCategories();
    Category newCategory(Category category);
    Category getCategory(int id);
    Category updateCategory(int id, Category category);
    boolean deleteCategory(int id);
}