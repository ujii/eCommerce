package kr.ac.hansung.cse.controller;

import kr.ac.hansung.cse.entity.Category;
import kr.ac.hansung.cse.exception.NotFoundException;
import kr.ac.hansung.cse.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@RestController
@RequestMapping(path = "/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping // 모든 category를 조회한다.
    public ResponseEntity<List<Category>> retrieveAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}") // 특정 id를 가진 category를 조회한다.
    public ResponseEntity<Category> retrieveCategory(@PathVariable Long id) {

        // pathVariable의 id를 가진 category를 찾는다.
        Category category = categoryService.getCategoryById(id);

        // category가 없다면 오류 반환
        if (category == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryDto request) {
        Category category = categoryService.createCategory(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDto request) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            throw new NotFoundException(id);
        }
        category.setName(request.getName());
        categoryService.updateCategory(category);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            throw new NotFoundException(id);
        }
        categoryService.deleteCategory(category);
        return ResponseEntity.noContent().build();
    }

    static class CategoryDto {
        @NotNull(message = "name is required")
        @Size(message = "name must be equal to or lower than 100", min = 1, max = 100)
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
