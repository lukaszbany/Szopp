package pl.betweenthelines.szopp.service.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Category;
import pl.betweenthelines.szopp.domain.repository.CategoryRepository;
import pl.betweenthelines.szopp.exception.InvalidParentCategoryException;
import pl.betweenthelines.szopp.exception.NotFoundException;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Category addCategory(String name, String description, Long parentId) {
        Category category = Category.builder()
                .name(name)
                .description(description)
                .build();

        if (parentId != null) {
            addParentCategory(category, parentId);
        }

        log.info("Adding new category: {}", category);
        return categoryRepository.save(category);
    }

    private void addParentCategory(Category category, Long parentId) {
        Category parent = categoryRepository.findById(parentId)
                .orElseThrow(InvalidParentCategoryException::new);

        category.setParentCategory(parent);
    }

    @Transactional
    public Category modifyCategory(Long categoryId, String name, String description, Long parentId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundException::new);

        category.setName(name);
        category.setDescription(description);
        setParentCategory(parentId, category);


        return category;
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

        log.info("Deleting category {}", category);
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
