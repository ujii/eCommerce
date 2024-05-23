package kr.ac.hansung.cse.controller;

import kr.ac.hansung.cse.entity.Category;
import kr.ac.hansung.cse.entity.Product;
import kr.ac.hansung.cse.exception.NotFoundException;
import kr.ac.hansung.cse.service.CategoryService;
import kr.ac.hansung.cse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


/* API Endpoint for categories and products association
 *
 
 * Link / Unlink products
 * 
 * To see the current products for a given category, you can do a GET on
 * 		/api/categories/{categoryid}/products
 * 
 * To link / unlink products with categories you can use the following URL:
 * 		/api/categories/{categoryid}/products/{productid}
 */

@RestController
@RequestMapping(path = "/api/categories/{categoryId}/products")
public class CategoryProductsController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<Set<Product>> retrieveAllProducts(@PathVariable Long categoryId) {
		Category category = categoryService.getCategoryById(categoryId);
		if (category == null) {
			throw new NotFoundException(categoryId);
		}
		Set<Product> products = category.getProducts();
		if (products.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(products);
	}


	@PostMapping("/{productId}")
	public ResponseEntity<Product> addProduct(@PathVariable Long categoryId, @PathVariable Long productId) {
		Category category = categoryService.getCategoryById(categoryId);
		if (category == null) {
			throw new NotFoundException(categoryId);
		}

		Product product = productService.getProductById(productId);
		if (product == null) {
			throw new NotFoundException(productId);
		}

		if (productService.hasCategory(product, category)) {
			throw new IllegalArgumentException("Product " + productId + " already contains category " + categoryId);
		}

		productService.addCategory(product, category);

		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> removeProduct(@PathVariable Long categoryId, @PathVariable Long productId) {

	}
}