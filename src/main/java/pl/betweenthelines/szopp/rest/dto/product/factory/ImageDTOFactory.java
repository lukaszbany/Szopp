package pl.betweenthelines.szopp.rest.dto.product.factory;

import org.springframework.stereotype.Component;
import pl.betweenthelines.szopp.domain.ProductImage;
import pl.betweenthelines.szopp.rest.dto.product.ImageDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImageDTOFactory {

    public List<ImageDTO> buildImageDTOs(List<ProductImage> productImages) {
        return productImages.stream()
                .map(this::buildImageDTO)
                .collect(Collectors.toList());
    }

    public ImageDTO buildImageDTO(ProductImage productImage) {
        return ImageDTO.builder()
                .id(productImage.getId())
                .filename(productImage.getFilename())
                .description(productImage.getDescription())
                .productId(getProductId(productImage))
                .order(productImage.getOrder())
                .build();
    }

    private Long getProductId(ProductImage productImage) {
        return productImage.getProduct().getId();
    }
}
