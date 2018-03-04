package com.bazalytskyi.test.dao;

import java.util.List;


import com.bazalytskyi.test.data.entities.Product;

public interface IProductReposetory {
	List<Product> getAllProducts(int idCategory);

	Product getProductById(int categoryId,int productId);

	void addProduct(Product product);

	void updateProduct(Product product);

	void deleteProduct(int categoryId,int productId);
	
}
