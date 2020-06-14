package pl.betweenthelines.szopp.rest.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;

    private String description;

    private String shortDescription;

    private BigDecimal price;

    private Long categoryId;

    private Integer inStock;

    private List<ImageDTO> images;

    private boolean active;

}
