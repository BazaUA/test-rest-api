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
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;

	@GetMapping("/categories/")
	public ResponseEntity<List<CategoryDTO>> getAllCategories(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "description", required = false) String description) {
		List<CategoryDTO> dtoList = convertToCategoriesDTO(categoryService.getAllCategories(name, description));
		return new ResponseEntity<List<CategoryDTO>>(dtoList, HttpStatus.OK);

	}

	private List<CategoryDTO> convertToCategoriesDTO(List<Category> list) {
		List<CategoryDTO> res = new ArrayList<>();
		for (Category c : list) {
			res.add(convertToCategoryDTO(c));
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

	@PutMapping("category")
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

	@PostMapping("category")
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

}
