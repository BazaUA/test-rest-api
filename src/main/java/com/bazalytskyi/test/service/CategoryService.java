package com.bazalytskyi.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazalytskyi.test.dao.ICategoryReposetory;
import com.bazalytskyi.test.data.entities.Category;

@Service
public class CategoryService implements ICategoryService {
	@Autowired
	private ICategoryReposetory repository;

	@Override
	public List<Category> getAllCategories() {
		return repository.getAllCategories();
	}

	@Override
	public Category getCategoryById(int id) {
		return repository.getCategoryById(id);
	}

	@Override
	public synchronized boolean addCategory(Category category) {
		if(repository.isExists(category.getName(), category.getDescription())) {
			return false;
		}else {
			repository.addCategory(category);
			return true;
		}	
	}

	@Override
	public void updateCategory(Category category) {
		repository.updateCategory(category);
		
	}

	@Override
	public void deleteCategory(int categoryId) {
		repository.deleteCategory(categoryId);
		
	}
	
}
