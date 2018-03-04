package com.bazalytskyi.test.client;

import java.net.URI;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.bazalytskyi.test.data.entities.Category;
import com.bazalytskyi.test.data.entities.Product;

public class RestClient {
	public void getCategoryById() {
		int categoryId = 1;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8888/api/categories/{id}";
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<Category> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				Category.class, categoryId);
		Category category = responseEntity.getBody();
		System.out.println("Id:" + category.getId() + ", Name:" + category.getName() + ", Description:"
				+ category.getDescription());
	}

	public void getAllCategories() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8888/api/categories";
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<Category[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				Category[].class);
		Category[] categories = responseEntity.getBody();
		for (Category category : categories) {
			System.out.println("Id:" + category.getId() + ", Name:" + category.getName() + ", Description: "
					+ category.getDescription());
		}
	}

	public void addCategory() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8888/api/category";
		Category objCategory = new Category();
		objCategory.setName("Smartphones");
		objCategory.setDescription("Sell phones");
		HttpEntity<Category> requestEntity = new HttpEntity<Category>(objCategory, headers);
		URI uri = restTemplate.postForLocation(url, requestEntity);
		System.out.println(uri.getPath());
	}

	public void updateCategory() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8888/api/category";
		Category objCategory = new Category();
		objCategory.setId(2);
		objCategory.setName("Update:Smartphones");
		objCategory.setDescription("Update:Sell phones");
		HttpEntity<Category> requestEntity = new HttpEntity<Category>(objCategory, headers);
		restTemplate.put(url, requestEntity);
	}

	public void deleteCategory() {
		int categoryId = 2;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8888/api/category/" + categoryId;
		restTemplate.delete(url);
	}

	public void getProductById() {
		int categoryId = 1;
		int productId = 1;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8888/api/categories/{id_category}/products/{id_product}";
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<Product> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				Product.class, categoryId, productId);
		Product product = responseEntity.getBody();
		System.out.println(
				"Id:" + product.getId() + ", Name:" + product.getName() + ", Description:" + product.getDescription());
	}

	public void getAllProducts() {
		int categoryId = 1;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8888/api/categories/{id}/products";
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<Product[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				Product[].class, categoryId);
		Product[] categories = responseEntity.getBody();
		for (Product product : categories) {
			System.out.println("Id:" + product.getId() + ", Name:" + product.getName() + ", Description: "
					+ product.getDescription());
		}
	}

	public void addProduct() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8888/api/product";
		Product objCategory = new Product();
		objCategory.setName("IPhone");
		objCategory.setDescription("Iphone X");
		objCategory.setPrice(1000);
		HttpEntity<Product> requestEntity = new HttpEntity<Product>(objCategory, headers);
		URI uri = restTemplate.postForLocation(url, requestEntity);

	}

	public void updateProduct() {
		Category cat = new Category();
		cat.setId(1);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8888/api/product";
		Product objCategory = new Product();
		objCategory.setId(1);
		objCategory.setName("Update:IPhone");
		objCategory.setDescription("Update:Iphone X");
		objCategory.setPrice(999);
		objCategory.setCategory(cat);
		HttpEntity<Product> requestEntity = new HttpEntity<Product>(objCategory, headers);
		restTemplate.put(url, requestEntity);
	}

	public void deleteProduct() {
		int categoryId = 1;
		int productId = 1;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8888/api/categories/" + categoryId + "/product/" + productId;
		restTemplate.delete(url);
	}

	public static void main(String args[]) {
		RestClient client = new RestClient();
		// client.getCategoryById();
		// client.getAllCategories();
		// client.addCategory();
		// client.updateCategory();
		// client.deleteCategory();
		// client.getAllProducts();
		// client.getCategoryById();
		// client.addProduct();
		// client.updateProduct();
		//client.deleteProduct();
	}
}