package com.bazalytskyi.test.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
	public Product getProductById(int categoryId, int productId) {
		String hql = "FROM Product as p WHERE p.category.id = ? and p.id = ?";
		return (Product) entityManager.createQuery(hql).setParameter(1, categoryId).setParameter(2, productId)
				.getSingleResult();
	}

	@Override
	public void addProduct(Product product) {
		entityManager.persist(product);
	}

	@Override
	public void updateProduct(Product product) {
		Product newProduct = this.getProductById(product.getCategory().getId(), product.getId());
		newProduct.setName(product.getName());
		newProduct.setDescription(product.getDescription());
		entityManager.flush();

	}

	@Override
	public void deleteProduct(int categoryId,int productId) {
		entityManager.remove(this.getProductById(categoryId, productId));
	}

}
