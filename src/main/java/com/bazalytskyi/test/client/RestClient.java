package com.bazalytskyi.test.client;

import java.net.URI;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.bazalytskyi.test.data.entities.Category;
import com.bazalytskyi.test.data.entities.CategoryDTO;
import com.bazalytskyi.test.data.entities.Product;
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
		String url = "http://localhost:8080/api/categories";
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
		objCategory.setName("Laptops");
		objCategory.setDescription("MacBooks");
		HttpEntity<CategoryDTO> requestEntity = new HttpEntity<CategoryDTO>(objCategory, headers);
		URI uri = restTemplate.postForLocation(url, requestEntity);
		System.out.println(uri.getPath());
	}

	public void updateCategory() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/api/category";
		Category objCategory = new Category();
		objCategory.setId(2);
		objCategory.setName("Update:Smartphones1");
		objCategory.setDescription("Update:Sell phones");
		HttpEntity<Category> requestEntity = new HttpEntity<Category>(objCategory, headers);
		restTemplate.put(url, requestEntity);
	}

	public void deleteCategory() {
		int categoryId = 2;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/api/category/" + categoryId;
		restTemplate.delete(url);
	}

	public void getProductById() {
		int categoryId = 1;
		int productId = 1;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/api/categories/{id_category}/products/{id_product}";
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<ProductDTO> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				ProductDTO.class, categoryId, productId);
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
		String url = "http://localhost:8080/api/categories/" + categoryId + "/product";
		ProductDTO objCategory = new ProductDTO();
		objCategory.setName("IPhone");
		objCategory.setDescription("Iphone X");
		objCategory.setPrice(1000);
		HttpEntity<ProductDTO> requestEntity = new HttpEntity<ProductDTO>(objCategory, headers);
		URI uri = restTemplate.postForLocation(url, requestEntity);

	}

	public void updateProduct() {
		int categoryId = 1;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/api/categories/" + categoryId + "/product";
		Product objCategory = new Product();
		objCategory.setId(2);
		objCategory.setName("Update:IPhone");
		objCategory.setDescription("Update:Iphone X");
		objCategory.setPrice(999);
		HttpEntity<Product> requestEntity = new HttpEntity<Product>(objCategory, headers);
		restTemplate.put(url, requestEntity);
	}

	public void deleteProduct() {
		int categoryId = 1;
		int productId = 2;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/api/categories/" + categoryId + "/product/" + productId;
		restTemplate.delete(url);
	}

	public static void main(String args[]) {
		RestClient client = new RestClient();
		// client.getCategoryById();
		// client.getAllCategories();
		 client.addCategory();
		// client.updateCategory();
		// client.deleteCategory();
		// client.getAllProducts();
		// client.getCategoryById();
		// client.addProduct();
		// client.updateProduct();
		// client.deleteProduct();
	}
}