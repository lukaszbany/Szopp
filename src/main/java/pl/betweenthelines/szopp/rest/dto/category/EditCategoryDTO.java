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

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    private String description;

    private Long parentCategoryId;

}
