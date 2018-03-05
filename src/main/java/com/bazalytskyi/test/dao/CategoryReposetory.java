package com.bazalytskyi.test.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.bazalytskyi.test.data.entities.Category;

@Transactional
@Repository
public class CategoryReposetory implements ICategoryReposetory {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Category> getAllCategories() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Category> cq = cb.createQuery(Category.class);
		Root<Category> rootEntry = cq.from(Category.class);
		CriteriaQuery<Category> all = cq.select(rootEntry);
		TypedQuery<Category> allQuery = entityManager.createQuery(all);
		return allQuery.getResultList();
	}

	@Override
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
	public boolean isIdExists(int id) {
		Category cat = entityManager.find(Category.class, id);
		return cat == null ? false : true;
	}

	@Override
	public List<Category> getAllCategoriesByNameAndDescrpt(String name, String description) {
		String hql = "FROM Category as cat WHERE cat.name = ? and cat.description = ?";
		List<Category> res = entityManager.createQuery(hql).setParameter(1, name).setParameter(2, description)
				.getResultList();
		return res;
	}

	@Override
	public List<Category> getAllCategoriesByName(String name) {
		String hql = "FROM Category as cat WHERE cat.name = ?";
		List<Category> res = entityManager.createQuery(hql).setParameter(1, name).getResultList();
		return res;
	}

	@Override
	public List<Category> getAllCategoriesByDescrpt(String description) {
		String hql = "FROM Category as cat WHERE cat.description = ?";
		List<Category> res = entityManager.createQuery(hql).setParameter(1, description).getResultList();
		return res;
	}

}
