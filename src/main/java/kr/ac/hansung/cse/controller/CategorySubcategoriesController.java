package kr.ac.hansung.cse.controller;

import java.util.List;
import java.util.Set;

import kr.ac.hansung.cse.entity.Category;
import kr.ac.hansung.cse.exception.NotFoundException;
import kr.ac.hansung.cse.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/* API Endpoint for categories and subcategories association
 * 
 * To see the current child categories for a given category, you can do a GET on
 * 		/categories/{parentid}/subcategories
 * 
 * Add / Remove child categories To associate / dis-associate a child category
 * 		with / from a parent category you can use the following URL:
 * 		/categories/{parentid}/subcategories/{childid} 
 * 
 */
@RestController
@RequestMapping(path = "/api/categories/{parentId}/subcategories")
public class CategorySubcategoriesController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Set<Category>> retrieveAllSubcategories(@PathVariable Long parentId) {
        Category parent = categoryService.getCategoryById(parentId);
        if (parent == null) {
            throw new NotFoundException(parentId);
        }
        Set<Category> subcategories = parent.getChildCategories();
        return ResponseEntity.ok(subcategories);
    }

    @PostMapping("/{childId}")
    public ResponseEntity<Category> addSubcategory(@PathVariable Long parentId, @PathVariable Long childId) {
        Category parent = categoryService.getCategoryById(parentId);
        if (parent == null) {
            throw new NotFoundException(parentId);
        }

        Category child = categoryService.getCategoryById(childId);
        if (child == null) {
            throw new NotFoundException(childId);
        }

        categoryService.addChildCategory(child, parent);
        return ResponseEntity.ok(child);
    }

    @DeleteMapping("/{childId}")
    public ResponseEntity<Void> removeSubcategory(@PathVariable Long parentId, @PathVariable Long childId) {
        Category parent = categoryService.getCategoryById(parentId);
        if (parent == null) {
            throw new NotFoundException(parentId);
        }

        Category child = categoryService.getCategoryById(childId);
        if (child == null) {
            throw new NotFoundException(childId);
        }

        if (!categoryService.isChildCategory(child, parent)) {
            throw new IllegalArgumentException("Category " + parentId + " does not contain subcategory " + childId);
        }

        categoryService.removeChildCategory(child, parent);
        return ResponseEntity.noContent().build();
    }
}