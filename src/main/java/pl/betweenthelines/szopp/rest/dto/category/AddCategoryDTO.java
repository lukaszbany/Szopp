package pl.betweenthelines.szopp.rest.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCategoryDTO {

    @NotBlank
    private String name;

    private String description;

    private Long parentCategoryId;

}
