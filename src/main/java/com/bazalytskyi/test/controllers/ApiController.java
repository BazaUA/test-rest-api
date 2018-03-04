package com.bazalytskyi.test.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.bazalytskyi.test.dao.*;
import com.bazalytskyi.test.data.entities.*;
import com.bazalytskyi.test.service.CategoryService;
import com.bazalytskyi.test.service.ProductService;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> list = categoryService.getAllCategories();
		return new ResponseEntity<List<Category>>(list, HttpStatus.OK);
	}

	@GetMapping("/categories/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") Integer id) {
		Category category = categoryService.getCategoryById(id);
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

	@PostMapping("category")
	public ResponseEntity<Void> addCategory(@RequestBody Category category, UriComponentsBuilder builder) {
		boolean flag = categoryService.addCategory(category);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/categories/{id}").buildAndExpand(category.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping("category")
	public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
		categoryService.updateCategory(category);
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

	@DeleteMapping("category/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable("id") Integer id) {
		categoryService.deleteCategory(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/categories/{id}/products")
	public ResponseEntity<List<Product>> getProductsById(@PathVariable("id") Integer id) {
		List<Product> list = productService.getAllProducts(id);
		return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
	}

	@GetMapping("/categories/{category_id}/products/{product_id}")
	public ResponseEntity<Product> getProductById(@PathVariable("category_id") Integer categoryId,
			@PathVariable("product_id") Integer productId) {
		Product product = productService.getProductById(categoryId, productId);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@PostMapping("product")
	public ResponseEntity<Void> addProduct(@RequestBody Product product) {
		productService.addProduct(product);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping("product")
	public ResponseEntity<Product> updateCategory(@RequestBody Product product) {
		productService.updateProduct(product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@DeleteMapping("/categories/{category_id}/product/{product_id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("category_id") Integer categoryId,@PathVariable("product_id") Integer productId) {
		productService.deleteProduct(categoryId,productId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
