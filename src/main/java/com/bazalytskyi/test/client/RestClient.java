package com.bazalytskyi.test.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.bazalytskyi.test.data.entities.Category;
import com.bazalytskyi.test.data.entities.CategoryDTO;
import com.bazalytskyi.test.data.entities.ProductDTO;

public class RestClient {
	public void getCategoryById() {
		int categoryId = 1;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/api/categories/{id}";
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<CategoryDTO> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				CategoryDTO.class, categoryId);
		CategoryDTO category = responseEntity.getBody();
		System.out.println("Name:" + category.getName() + ", Description:" + category.getDescription());
	}

	public void getAllCategories() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/api/categories/";
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<CategoryDTO[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				CategoryDTO[].class);
		CategoryDTO[] categories = responseEntity.getBody();
		for (CategoryDTO category : categories) {
			System.out.println("Name:" + category.getName() + ", Description: " + category.getDescription());
		}
	}

	public void addCategory() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/api/category";
		CategoryDTO objCategory = new CategoryDTO();
		objCategory.setName("Cars123");
		objCategory.setDescription("BMW and so on...1");
		HttpEntity<CategoryDTO> requestEntity = new HttpEntity<CategoryDTO>(objCategory, headers);
		restTemplate.put(url, requestEntity);
	}

	public void updateCategory() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/api/category";
		Category objCategory = new Category();
		objCategory.setId(1);
		objCategory.setName("Updated Cars12");
		objCategory.setDescription("Update:BMW and so on...1");
		HttpEntity<Category> requestEntity = new HttpEntity<Category>(objCategory, headers);
		restTemplate.postForLocation(url, requestEntity);
	}

	public void deleteCategory() {
		int categoryId = 6;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/api/category/" + categoryId;
		restTemplate.delete(url);
	}

	public void getProductById() {
		int productId = 3;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/api/products/{id_product}";
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<ProductDTO> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				ProductDTO.class, productId);
		ProductDTO product = responseEntity.getBody();
		System.out.println("Name:" + product.getName() + ", Description:" + product.getDescription());
	}

	public void getAllProducts() {
		int categoryId = 1;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/api/categories/{id}/products";
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<ProductDTO[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				ProductDTO[].class, categoryId);
		ProductDTO[] categories = responseEntity.getBody();
		for (ProductDTO product : categories) {
			System.out.println("Name:" + product.getName() + ", Description: " + product.getDescription());
		}
	}

	public void addProduct() {
		int categoryId = 1;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/api/product";
		ProductDTO objCategory = new ProductDTO();
		objCategory.setCategoryId(categoryId);
		objCategory.setName("IPhone");
		objCategory.setDescription("Iphone 9");
		objCategory.setPrice(1000);
		HttpEntity<ProductDTO> requestEntity = new HttpEntity<ProductDTO>(objCategory, headers);
		restTemplate.put(url, requestEntity);

	}

	public void updateProduct() {
		int categoryId = 1;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/api/product";
		ProductDTO objCategory = new ProductDTO();
		objCategory.setCategoryId(categoryId);
		objCategory.setId(4);
		objCategory.setName("Update:IPhone");
		objCategory.setDescription("Update:Iphone X");
		objCategory.setPrice(999);
		HttpEntity<ProductDTO> requestEntity = new HttpEntity<ProductDTO>(objCategory, headers);
		restTemplate.postForLocation(url, requestEntity);
	}

	public void deleteProduct() {
		int productId = 6;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/api/product/" + productId;
		restTemplate.delete(url);
	}

	public static void main(String args[]) {
		RestClient client = new RestClient();
		 //client.addCategory();
		 client.updateCategory();
		// client.getCategoryById();
		// client.getAllCategories();
		// client.deleteCategory();
		// client.addProduct();
		// client.updateProduct();
		// client.getAllProducts();
		// client.getProductById();

		// client.deleteProduct();
	}
}