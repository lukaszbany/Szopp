package pl.betweenthelines.szopp.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Category;
import pl.betweenthelines.szopp.domain.repository.CategoryRepository;
import pl.betweenthelines.szopp.exception.InvalidParentCategoryException;
import pl.betweenthelines.szopp.exception.NotFoundException;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public void addCategory(String name, String description, Long parentId) {
        Category category = Category.builder()
                .name(name)
                .description(description)
                .build();

        if (parentId != null) {
            addParentCategory(category, parentId);
        }

        categoryRepository.save(category);
    }

    private void addParentCategory(Category category, Long parentId) {
        Category parent = categoryRepository.findById(parentId)
                .orElseThrow(InvalidParentCategoryException::new);

        category.setParentCategory(parent);
    }

    @Transactional
    public void modifyCategory(Long categoryId, String name, String description, Long parentId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundException::new);

        category.setName(name);
        category.setDescription(description);
        setParentCategory(parentId, category);
    }

    private void setParentCategory(Long parentId, Category category) {
        if (parentId != null) {
            addParentCategory(category, parentId);
        } else {
            category.setParentCategory(null);
        }
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = getCategory(categoryId);

        categoryRepository.delete(category);
    }

    @Transactional
    public List<Category> getCategories() {
        return categoryRepository.findAllParents();
    }

    @Transactional
    public Category getCategory(Long id) {
        if (id == null) {
            throw new NotFoundException();
        }

        return categoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

}
