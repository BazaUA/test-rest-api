package com.bazalytskyi.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazalytskyi.test.dao.IProductReposetory;
import com.bazalytskyi.test.data.entities.Category;
import com.bazalytskyi.test.data.entities.Product;

@Service
public class ProductService implements IProductService {
	@Autowired
	private IProductReposetory reposetory;

	@Override
	public List<Product> getAllProducts(int categoryId) {
		return reposetory.getAllProducts(categoryId);
	}
	
	@Override
	public Long getNumberOfProducts(int categoryId) {
		return reposetory.getNumberOfProducts(categoryId);
	}

	@Override
	public Product getProductById(int productId) {
		return reposetory.getProductById(productId);
	}

	@Override
	public void addProduct(Product product) {
		reposetory.addProduct(product);
	}

	@Override
	public void updateProduct(Product product) {
		reposetory.updateProduct(product);

	}

	@Override
	public void deleteProduct(int productId) {
		reposetory.deleteProduct(productId);
	}

}
