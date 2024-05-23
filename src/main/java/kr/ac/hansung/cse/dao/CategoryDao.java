package kr.ac.hansung.cse.dao;

import kr.ac.hansung.cse.entity.Category;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CategoryDao {

	@PersistenceContext
	private EntityManager entityManager;

	public Category getCategoryById(Long id) {
		return entityManager.find(Category.class, id);
	}

	public List<Category> getCategories() {
		TypedQuery<Category> query = entityManager.createQuery("SELECT c FROM Category c", Category.class);
		return query.getResultList();
	}

	public Long addCategory(Category category) {
		entityManager.persist(category);
		entityManager.flush();
		return category.getId();
	}

	public void deleteCategory(Category category) {
		entityManager.remove(category);
		entityManager.flush();
	}

	public void updateCategory(Category category) {
		entityManager.merge(category);
		entityManager.flush();
	}
}
