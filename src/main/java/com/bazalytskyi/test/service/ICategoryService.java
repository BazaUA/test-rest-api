package com.bazalytskyi.test.service;

import java.util.List;

import com.bazalytskyi.test.data.entities.Category;

public interface ICategoryService {
	List<Category> getAllCategories();

	Category getCategoryById(int id);

	boolean addCategory(Category category);

	void updateCategory(Category category);

	void deleteCategory(int categoryId);

	boolean isCategoryIdExist(int categoryId);

	List<Category> getAllCategoriesByNameAndDescrpt(String name, String description);

	List<Category> getAllCategoriesByName(String name);

	List<Category> getAllCategoriesByDescrpt(String description);
}
