package com.bazalytskyi.test.service;

import java.util.List;

import com.bazalytskyi.test.data.entities.Category;
import com.bazalytskyi.test.data.entities.Product;

public interface IProductService {
	List<Product> getAllProducts(int categoryId);

	Product getProductById(int productId);

	void addProduct(Product product);

	void updateProduct(Product product);

	void deleteProduct(int productId);

	Long getNumberOfProducts(int categoryId);
}
