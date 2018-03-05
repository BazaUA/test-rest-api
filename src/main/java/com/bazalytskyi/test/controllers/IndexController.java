package com.bazalytskyi.test.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bazalytskyi.test.data.entities.CategoryDTO;
import com.bazalytskyi.test.service.CategoryService;
import com.bazalytskyi.test.service.ProductService;

@Controller
public class IndexController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;

	@GetMapping("/")
	public ModelAndView index() {
		Map<String, String> model = new HashMap<>();
		model.put("name", "Alexey");

		
		return new ModelAndView("index", model);
	}
}