package com.bazalytskyi.test.dao;

import java.util.List;

import com.bazalytskyi.test.data.entities.Category;

public interface ICategoryRepository {
	List<Category> getAllCategories(String name, String description);

	Category getCategoryById(int id);

	Category addCategory(Category category);

	void updateCategory(Category category);

	void deleteCategory(int categoryId);

	boolean isExists(String name, String description);

	boolean isIdExists(int id);
}
