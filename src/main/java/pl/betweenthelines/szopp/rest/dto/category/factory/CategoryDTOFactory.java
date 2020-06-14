package pl.betweenthelines.szopp.rest.dto.category.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.betweenthelines.szopp.domain.Category;
import pl.betweenthelines.szopp.exception.NotFoundException;
import pl.betweenthelines.szopp.rest.dto.category.CategoryDTO;
import pl.betweenthelines.szopp.security.AuthenticationService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static pl.betweenthelines.szopp.security.domain.RoleName.ADMIN;
import static pl.betweenthelines.szopp.security.domain.RoleName.STAFF;

@Component
public class CategoryDTOFactory {

    @Autowired
    private AuthenticationService authenticationService;

    public List<CategoryDTO> buildCategoryDTOs(List<Category> categories) {
        boolean notActiveVisible = authenticationService.hasAnyRole(ADMIN, STAFF);

        return categories.stream()
                .filter(category -> shouldBeVisible(category, notActiveVisible))
                .map(category -> buildCategoryDTO(category, notActiveVisible))
                .sorted(byId())
                .collect(Collectors.toList());
    }

    private Comparator<CategoryDTO> byId() {
        return Comparator.comparing(CategoryDTO::getId);
    }

    private boolean shouldBeVisible(Category category, boolean notActiveVisible) {
        return notActiveVisible || isCategoryActive(category);
    }

    private boolean isCategoryActive(Category category) {
        return category == null ||
                (category.isActive() && isCategoryActive(category.getParentCategory()));
    }

    public CategoryDTO buildCategoryDTO(Category category) {
        boolean notActiveVisible = authenticationService.hasAnyRole(ADMIN, STAFF);

        return buildCategoryDTO(category, notActiveVisible);
    }

    private CategoryDTO buildCategoryDTO(Category category, boolean notActiveVisible) {
        if (!shouldBeVisible(category, notActiveVisible)) {
            throw new NotFoundException();
        }

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .parentCategoryId(addParent(category))
                .childCategories(addChildren(category, notActiveVisible))
                .active(category.isActive())
                .build();
    }

    private Long addParent(Category category) {
        Category parentCategory = category.getParentCategory();
        if (parentCategory != null) {
            return parentCategory.getId();
        }

        return null;
    }

    private List<CategoryDTO> addChildren(Category category, boolean notActiveVisible) {
        return category.getChildCategories()
                .stream()
                .filter(child -> shouldBeVisible(child, notActiveVisible))
                .map(child -> buildCategoryDTO(child, notActiveVisible))
                .sorted(byId())
                .collect(Collectors.toList());
    }


}
