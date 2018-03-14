package com.bazalytskyi.test.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bazalytskyi.test.data.entities.Category;
import com.bazalytskyi.test.data.entities.Product;

@Transactional
@Repository
public class ProductReposetory implements IProductReposetory {
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAllProducts(int idCategory) {
		String hql = "FROM Product as p WHERE p.category.id = ?";
		return entityManager.createQuery(hql).setParameter(1, idCategory).getResultList();
	}

	@Override
	@Cacheable(value = "categoryByIdCount")
	public Long getNumberOfProducts(int idCategory) {
		String hql = "SELECT count(*) FROM Product as p WHERE p.category.id = ?";
		return (Long) entityManager.createQuery(hql).setParameter(1, idCategory).getResultList().get(0);
	}

	@Override
	@Cacheable(value = "pduductById")
	public Product getProductById(int productId) {
		return entityManager.find(Product.class, productId);
	}

	@Override
	public void addProduct(Product product) {
		entityManager.persist(product);
	}

	@Override
	public void updateProduct(Product product) {
		Product newProduct = this.getProductById(product.getId());
		newProduct.setName(product.getName());
		newProduct.setDescription(product.getDescription());
		entityManager.flush();

	}

	@Override
	public void deleteProduct(int productId) {
		entityManager.remove(this.getProductById(productId));
	}

}
