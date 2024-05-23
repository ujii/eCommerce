package kr.ac.hansung.cse.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import kr.ac.hansung.cse.entity.Product;
import kr.ac.hansung.cse.exception.NotFoundException;
import kr.ac.hansung.cse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.Getter;
import lombok.Setter;

/*
URL: /api/products

To get list of products:  GET "http://localhost:8080/ecommerce/api/products"
To get products info:  GET "http://localhost:8080/ecommerce/api/products/{id}"
To create products:   POST "http://localhost:8080/ecommerce/api/products" -d '{ "name": "P1", "price": 100.00 }'
To update products:   PUT "http://localhost:8080/ecommerce/api/products/{id}" -d '{ "name": "P1", "price": 100.00 }'
To delete products: DELETE "http://localhost:8080/ecommerce/api/products/{id}"
*/

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<List<Product>> retrieveAllProducts() {
		List<Product> products = productService.getAllProducts();
		if (products.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(products);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> retrieveProduct(@PathVariable Long id) {
		Product product = productService.getProductById(id);
		if (product == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(product);
	}

	// DTO(Data Transfer Object) : 계층간 데이터 교환을 위한 객체, 여기서는 클라이언트(Postman)에서 오는 데이터를 수신할 목적으로 사용
    // Product와 ProductDto와의 차이를 비교해서 살펴보기 바람

	@PostMapping
	public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDto request) {
		Product product = productService.createProduct(request.getName(), request.getPrice());
		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}



	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto request) {


	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		Product product = productService.getProductById(id);
		System.out.println(product);
		if (product == null) {
			throw new NotFoundException(id);
		}

		productService.deleteProduct(product);
		return ResponseEntity.noContent().build(); // new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Getter
	@Setter
	static class ProductDto {
		
        @NotNull(message = "name is required")
        @Size(message = "name must be equal to or lower than 300", min = 1, max = 300)
        private String name;           
        
        @NotNull(message = "name is required")
		@Min(value = 0, message = "price must be greater than or equal to 0")
        private Double price;
	}
}