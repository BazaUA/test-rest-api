package com.bazalytskyi.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.bazalytskyi.test.dao.ICategoryRepository;
import com.bazalytskyi.test.data.entities.Category;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class CategoryService implements ICategoryService {
	@Autowired
	private ICategoryRepository repository;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public List<Category> getAllCategories(String name, String description) {
		return repository.getAllCategories(name, description);
	}

	@Override
	public Category getCategoryById(int id) {
		return repository.getCategoryById(id);
	}

	@Override
	public Category addCategory(Category category) {
		Category res=transactionTemplate.execute(new TransactionCallback<Category>() {
			public Category doInTransaction(TransactionStatus txStatus) {
				try {

					if (repository.isExists(category.getName(), category.getDescription())) {
						return null;
					} else {
						repository.addCategory(category);
						return category;
					}
				} catch (RuntimeException e) {
					txStatus.setRollbackOnly();
					throw e;
				}
			}
		});
		return res;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void updateCategory(Category category) {
		repository.updateCategory(category);

	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void deleteCategory(int categoryId) {
		repository.deleteCategory(categoryId);

	}

	@Override
	public boolean isCategoryIdExist(int categoryId) {
		return repository.isIdExists(categoryId);
	}

}
