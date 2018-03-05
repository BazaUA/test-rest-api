package com.bazalytskyi.test.dao;

import java.util.List;


import com.bazalytskyi.test.data.entities.Product;

public interface IProductReposetory {
	List<Product> getAllProducts(int idCategory);

	Product getProductById(int productId);

	void addProduct(Product product);

	void updateProduct(Product product);

	void deleteProduct(int productId);

	Long getNumberOfProducts(int idCategory);
	
}
