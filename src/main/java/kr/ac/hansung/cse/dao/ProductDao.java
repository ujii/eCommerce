package kr.ac.hansung.cse.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import kr.ac.hansung.cse.entity.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class ProductDao {

	@PersistenceContext
	private EntityManager entityManager;

	public Product getProductById(Long id) {
		return entityManager.find(Product.class, id);
	}

	public List<Product> getProducts() {
		TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p", Product.class);
		return query.getResultList();
	}

	public Long addProduct(Product product) {
		entityManager.persist(product);
		entityManager.flush();
		return product.getId();
	}

	public void deleteProduct(Product product) {
		entityManager.remove(product);
		entityManager.flush();
	}

	public void updateProduct(Product product) {
		entityManager.merge(product);
		entityManager.flush();
	}
}
