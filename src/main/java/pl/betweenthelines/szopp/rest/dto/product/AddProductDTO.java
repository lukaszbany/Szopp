package pl.betweenthelines.szopp.rest.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductDTO {

    @NotBlank(message = "{validation.product.name.blank}")
    private String name;

    private String description;

    private String shortDescription;

    @Max(value = 99999, message = "{validation.product.price.max}")
    @Min(value = 0, message = "{validation.product.price.negative}")
    @NotNull(message = "{validation.product.price.blank}")
    private BigDecimal price;

    @NotNull(message = "{validation.product.category.blank}")
    private Long categoryId;

    @Min(value = 0, message = "{validation.product.stock.negative}")
    @Max(value = 999, message = "{validation.product.stock.max}")
    @NotNull(message = "{validation.product.stock.blank}")
    private Integer inStock;

    private boolean active;

}
