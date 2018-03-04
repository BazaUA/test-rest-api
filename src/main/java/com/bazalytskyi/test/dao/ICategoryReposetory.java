package com.bazalytskyi.test.dao;

import java.util.List;

import com.bazalytskyi.test.data.entities.Category;

public interface ICategoryReposetory {
	List<Category> getAllCategories();

	Category getCategoryById(int id);

	void addCategory(Category category);

	void updateCategory(Category category);

	void deleteCategory(int categoryId);
	
	boolean isExists(String name,String description);
}
