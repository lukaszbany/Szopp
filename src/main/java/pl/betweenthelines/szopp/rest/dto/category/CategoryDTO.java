package pl.betweenthelines.szopp.rest.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long id;

    private String name;

    private String description;

    private Long parentCategoryId;

    private List<CategoryDTO> childCategories;

    private boolean active;

}
