package com.bazalytskyi.test.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bazalytskyi.test.dao.CategoryRepository;
import com.bazalytskyi.test.data.entities.Category;
import com.bazalytskyi.test.data.entities.CategoryDTO;
import com.bazalytskyi.test.data.entities.Product;
import com.bazalytskyi.test.data.entities.ProductDTO;
import com.bazalytskyi.test.service.CategoryService;
import com.bazalytskyi.test.service.ProductService;
import com.google.gson.Gson;

@Controller
public class IndexController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;

	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("index");
		List<CategoryDTO> allCategories=convertToCategoriesDTO(categoryService.getAllCategories(null, null));
		
		
		view.addObject("list",allCategories);
		return view;
	}
	
	private List<CategoryDTO> convertToCategoriesDTO(List<Category> list) {
		List<CategoryDTO> res = new ArrayList<>();
		for (Category c : list) {
			res.add(convertToCategoryDTO(c));
		}
		return res;
	}
	
	private CategoryDTO convertToCategoryDTO(Category cat) {
		CategoryDTO dto = new CategoryDTO();
		dto.setId(cat.getId());
		dto.setName(cat.getName());
		dto.setDescription(cat.getDescription());
		dto.setTotal(productService.getNumberOfProducts(cat.getId()));
		return dto;
	}
}