package pl.betweenthelines.szopp.rest.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EditProductDTO {

    @NotNull(message = "{validation.product.id.null}")
    private Long id;

    @NotBlank(message = "{validation.product.name.blank}")
    private String name;

    private String description;

    @NotNull(message = "{validation.product.price.blank}")
    private BigDecimal price;

    @NotNull(message = "{validation.product.category.blank}")
    private Long categoryId;

    @Min(value = 0, message = "{validation.product.stock.negative}")
    @NotNull(message = "{validation.product.stock.blank}")
    private Integer inStock;

}
