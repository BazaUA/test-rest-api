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
import org.springframework.web.bind.annotation.RestController;
import com.bazalytskyi.test.data.entities.Product;
import com.bazalytskyi.test.data.entities.ProductDTO;
import com.bazalytskyi.test.service.CategoryService;
import com.bazalytskyi.test.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;

	@GetMapping("/categories/{id}/products")
	public ResponseEntity<List<ProductDTO>> getAllProductsByCategoryId(@PathVariable("id") Integer id) {
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
			res.add(convertToProductDTO(c));
		}
		return res;
	}

	@GetMapping("/products/{product_id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable("product_id") Integer productId) {
		ProductDTO product = convertToProductDTO(productService.getProductById(productId));
		return new ResponseEntity<ProductDTO>(product, HttpStatus.OK);

	}

	private ProductDTO convertToProductDTO(Product product) {
		ProductDTO dto = new ProductDTO();
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		return dto;
	}

	@PutMapping("/product")
	public ResponseEntity<Void> addProduct(@RequestBody ProductDTO productDTO) {
		Product product = convertToProduct(productDTO);
		productService.addProduct(product);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	private Product convertToProduct(ProductDTO productDTO) {
		Product res = new Product();
		res.setName(productDTO.getName());
		res.setDescription(productDTO.getDescription());
		res.setPrice(productDTO.getPrice());
		res.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()));
		return res;
	}

	@PostMapping("/product")
	public ResponseEntity<Product> updateProduct(@RequestBody ProductDTO productDTO) {
		Product product = convertToProduct(productDTO);
		product.setId(productDTO.getId());
		productService.updateProduct(product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@DeleteMapping("/product/{product_id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("product_id") Integer productId) {
		productService.deleteProduct(productId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
