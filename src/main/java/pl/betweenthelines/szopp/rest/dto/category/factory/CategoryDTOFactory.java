package pl.betweenthelines.szopp.rest.dto.category.factory;

import org.springframework.stereotype.Component;
import pl.betweenthelines.szopp.domain.Category;
import pl.betweenthelines.szopp.rest.dto.category.CategoryDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryDTOFactory {

    public List<CategoryDTO> buildCategoryDTOs(List<Category> categories) {
        return categories.stream()
                .map(this::buildCategoryDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO buildCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .parentCategoryId(addParent(category))
                .childCategories(addChildren(category))
                .build();
    }

    private Long addParent(Category category) {
        Category parentCategory = category.getParentCategory();
        if (parentCategory != null) {
            return parentCategory.getId();
        }

        return null;
    }

    private List<CategoryDTO> addChildren(Category category) {
        return category.getChildCategories()
                .stream()
                .map(this::buildCategoryDTO)
                .collect(Collectors.toList());
    }
}
