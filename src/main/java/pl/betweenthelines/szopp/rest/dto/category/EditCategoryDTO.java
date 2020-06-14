package pl.betweenthelines.szopp.rest.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditCategoryDTO {

    @NotNull(message = "{validation.category.id.null}")
    private Long id;

    @NotBlank(message = "{validation.category.name.blank}")
    private String name;

    private String description;

    private Long parentCategoryId;

    private boolean active;

}
