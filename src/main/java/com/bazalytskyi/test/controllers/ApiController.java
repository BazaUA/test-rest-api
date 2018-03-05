package com.bazalytskyi.test.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping("/categories/")
	public ResponseEntity<List<CategoryDTO>> getAllCategories(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "description", required = false) String description) {
		if (name != null && description != null) {
			List<CategoryDTO> dtoList = convertToCategoriesDTO(
					categoryService.getAllCategoriesByNameAndDescrpt(name, description));
			return new ResponseEntity<List<CategoryDTO>>(dtoList, HttpStatus.OK);
		} else if (description == null && name != null) {

			List<CategoryDTO> dtoList = convertToCategoriesDTO(categoryService.getAllCategoriesByName(name));
			return new ResponseEntity<List<CategoryDTO>>(dtoList, HttpStatus.OK);
		} else if (name == null && description != null) {
			List<CategoryDTO> dtoList = convertToCategoriesDTO(categoryService.getAllCategoriesByDescrpt(description));
			return new ResponseEntity<List<CategoryDTO>>(dtoList, HttpStatus.OK);
		} else {
			List<CategoryDTO> dtoList = convertToCategoriesDTO(categoryService.getAllCategories());
			return new ResponseEntity<List<CategoryDTO>>(dtoList, HttpStatus.OK);
		}
	}

	private List<CategoryDTO> convertToCategoriesDTO(List<Category> list) {
		List<CategoryDTO> res = new ArrayList<>();
		for (Category c : list) {
			CategoryDTO temp = new CategoryDTO();
			temp.setName(c.getName());
			temp.setDescription(c.getDescription());
			temp.setTotal(productService.getNumberOfProducts(c.getId()));
			res.add(temp);
		}
		return res;
	}

	@GetMapping("/categories/{id}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Integer id) {
		if (!categoryService.isCategoryIdExist(id)) {
			return new ResponseEntity<CategoryDTO>(HttpStatus.NOT_FOUND);
		}
		CategoryDTO categoryDTO = convertToCategoryDTO(categoryService.getCategoryById(id));
		return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);

	}

	private CategoryDTO convertToCategoryDTO(Category cat) {
		CategoryDTO dto = new CategoryDTO();
		dto.setName(cat.getName());
		dto.setDescription(cat.getDescription());
		dto.setTotal(productService.getNumberOfProducts(cat.getId()));
		return dto;
	}

	@PostMapping("category")
	public ResponseEntity<Void> addCategory(@RequestBody CategoryDTO categoryDTO, UriComponentsBuilder builder) {
		Category category = convertToCategory(categoryDTO);
		boolean flag = categoryService.addCategory(category);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/categories/{id}").buildAndExpand(category.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	private Category convertToCategory(CategoryDTO categoryDTO) {
		Category res = new Category();
		res.setName(categoryDTO.getName());
		res.setDescription(categoryDTO.getDescription());
		return res;
	}

	@PutMapping("category")
	public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
		categoryService.updateCategory(category);
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

	@DeleteMapping("category/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable("id") Integer id) {
		if (!categoryService.isCategoryIdExist(id))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		categoryService.deleteCategory(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/categories/{id}/products")
	public ResponseEntity<List<ProductDTO>> getProductsById(@PathVariable("id") Integer id) {
		if (categoryService.isCategoryIdExist(id)) {
			List<ProductDTO> list = convertToListProductsDTO(productService.getAllProducts(id));
			return new ResponseEntity<List<ProductDTO>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<ProductDTO>>(HttpStatus.NOT_FOUND);
		}
	}

	private List<ProductDTO> convertToListProductsDTO(List<Product> allProducts) {
		List<ProductDTO> res = new ArrayList<>();
		for (Product c : allProducts) {
			ProductDTO temp = new ProductDTO();
			temp.setName(c.getName());
			temp.setDescription(c.getDescription());
			temp.setPrice(c.getPrice());
			res.add(temp);
		}
		return res;
	}

	@GetMapping("/categories/{category_id}/products/{product_id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable("category_id") Integer categoryId,
			@PathVariable("product_id") Integer productId) {
		if (categoryService.isCategoryIdExist(categoryId)) {
			ProductDTO product = converToProductDTO(productService.getProductById(categoryId, productId));
			return new ResponseEntity<ProductDTO>(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
	}

	private ProductDTO converToProductDTO(Product product) {
		ProductDTO dto = new ProductDTO();
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		return dto;
	}

	@PostMapping("/categories/{id}/product")
	public ResponseEntity<Void> addProduct(@RequestBody ProductDTO productDTO, @PathVariable("id") Integer id) {
		if (!categoryService.isCategoryIdExist(id))
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		Product product = convertToProduct(productDTO, id);
		productService.addProduct(product);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	private Product convertToProduct(ProductDTO productDTO, int categoryId) {
		Product res = new Product();
		res.setName(productDTO.getName());
		res.setDescription(productDTO.getDescription());
		res.setPrice(productDTO.getPrice());
		res.setCategory(categoryService.getCategoryById(categoryId));
		return res;
	}

	@PutMapping("/categories/{id}/product")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable("id") Integer id) {
		if (!categoryService.isCategoryIdExist(id))
			return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
		product.setCategory(categoryService.getCategoryById(id));
		productService.updateProduct(product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@DeleteMapping("/categories/{category_id}/product/{product_id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("category_id") Integer categoryId,
			@PathVariable("product_id") Integer productId) {
		if (categoryService.isCategoryIdExist(categoryId)) {
			productService.deleteProduct(categoryId, productId);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

}
