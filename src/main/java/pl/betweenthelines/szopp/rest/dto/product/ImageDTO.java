package pl.betweenthelines.szopp.rest.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {

    private Long id;

    private String filename;

    private String description;

    private Long productId;

    private int order;

}
