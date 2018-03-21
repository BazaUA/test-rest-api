package com.bazalytskyi.test.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.bazalytskyi.test.data.entities.Category;
import com.bazalytskyi.test.data.entities.Category_;

@Repository
public class CategoryRepository implements ICategoryRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Category> getAllCategories(String name, String description) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Category> cq = qb.createQuery(Category.class);
		Root<Category> rootEntry = cq.from(Category.class);
		if (name != null) {
			Predicate namePredicate = qb.equal(rootEntry.get(Category_.name), name);
			cq.where(namePredicate);
		}
		if (description != null) {
			Predicate descriptionPredicate = qb.equal(rootEntry.get(Category_.description), description);
			cq.where(descriptionPredicate);
		}
		CriteriaQuery<Category> all = cq.select(rootEntry);
		TypedQuery<Category> allQuery = entityManager.createQuery(all);
		return allQuery.getResultList();
	}

	@Override
	@Cacheable(value = "categoryById")
	public Category getCategoryById(int id) {
		return entityManager.find(Category.class, id);
	}

	@Override
	public void addCategory(Category category) {
		entityManager.persist(category);
	}

	@Override
	public void updateCategory(Category category) {
		Category newCategory = this.getCategoryById(category.getId());
		newCategory.setName(category.getName());
		newCategory.setDescription(category.getDescription());
		entityManager.flush();

	}

	@Override
	public void deleteCategory(int categoryId) {
		entityManager.remove(getCategoryById(categoryId));
	}

	@Override
	public boolean isExists(String name, String description) {
		String hql = "FROM Category as cat WHERE cat.name = ? and cat.description = ?";
		int count = entityManager.createQuery(hql).setParameter(1, name).setParameter(2, description).getResultList()
				.size();
		return count > 0 ? true : false;
	}

	@Override
	@Cacheable(value = "isCategoryById")
	public boolean isIdExists(int id) {
		Category cat = entityManager.find(Category.class, id);
		return cat == null ? false : true;
	}

}
