package kr.ac.hansung.cse.service;

import java.util.List;

import kr.ac.hansung.cse.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.hansung.cse.dao.CategoryDao;
import kr.ac.hansung.cse.entity.Category;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.getCategories();
    }
    
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.getCategoryById(id);
    }
    
    @Override
    public Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);

        Long id =  categoryRepository.addCategory(category);
        category.setId(id);
        
        return category;
    }
           
    @Override
    public void updateCategory(Category category) {    	
        categoryRepository.updateCategory(category);
    }
    
    @Override
    public void deleteCategory(Category category) {
        Category attachedCategory = getCategoryById(category.getId());
        categoryRepository.deleteCategory(attachedCategory);
    }
    
    @Override
    public boolean isChildCategory(Category category, Category parent) {
    	if(category.getParent() == null)
    		return false;
    	
        return category.getParent().equals(parent);
    }
    
    @Override
    public void addChildCategory(Category category, Category parent) {
        category.setParent(parent);
        categoryRepository.updateCategory(category);
    }
    
    @Override
    public void removeChildCategory(Category category, Category parent) {
        category.setParent(null);
        categoryRepository.updateCategory(category);
    }

}