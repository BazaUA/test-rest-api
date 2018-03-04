package com.bazalytskyi.test.service;

import java.util.List;

import com.bazalytskyi.test.data.entities.Category;

public interface ICategoryService {
	List<Category> getAllCategories();

	Category getCategoryById(int id);

	boolean addCategory(Category category);

	void updateCategory(Category category);

	void deleteCategory(int categoryId);
}
