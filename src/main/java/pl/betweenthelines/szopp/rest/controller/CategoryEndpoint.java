package pl.betweenthelines.szopp.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.betweenthelines.szopp.domain.Category;
import pl.betweenthelines.szopp.rest.dto.category.AddCategoryDTO;
import pl.betweenthelines.szopp.rest.dto.category.CategoryDTO;
import pl.betweenthelines.szopp.rest.dto.category.EditCategoryDTO;
import pl.betweenthelines.szopp.rest.dto.category.factory.CategoryDTOFactory;
import pl.betweenthelines.szopp.service.category.CategoryService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/categories")
public class CategoryEndpoint {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryDTOFactory categoryDTOFactory;

    @RequestMapping(method = GET)
    public List<CategoryDTO> getCategories() {
        List<Category> categories = categoryService.getCategories();

        return categoryDTOFactory.buildCategoryDTOs(categories);
    }

    @RequestMapping(method = GET, value = "/{id}")
    public CategoryDTO getCategory(@PathVariable Long id) {
        Category category = categoryService.getCategory(id);

        return categoryDTOFactory.buildCategoryDTO(category);
    }

    @RequestMapping(method = POST)
    public CategoryDTO addCategory(@RequestBody @Valid AddCategoryDTO addCategoryDTO) {
        String name = addCategoryDTO.getName();
        String description = addCategoryDTO.getDescription();
        Long parentId = addCategoryDTO.getParentCategoryId();

        Category newCategory = categoryService.addCategory(name, description, parentId);

        return categoryDTOFactory.buildCategoryDTO(newCategory);
    }

    @RequestMapping(method = PUT)
    public CategoryDTO editCategory(@RequestBody @Valid EditCategoryDTO editCategoryDTO) {
        Long id = editCategoryDTO.getId();
        String name = editCategoryDTO.getName();
        String description = editCategoryDTO.getDescription();
        Long parentId = editCategoryDTO.getParentCategoryId();

        Category category = categoryService.modifyCategory(id, name, description, parentId);

        return categoryDTOFactory.buildCategoryDTO(category);
    }

    @RequestMapping(method = DELETE, value = "{id}")
    public String deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);

        return "OK";
    }


}
