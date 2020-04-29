package pl.betweenthelines.szopp.rest.dto.product.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.betweenthelines.szopp.domain.Category;
import pl.betweenthelines.szopp.domain.Product;
import pl.betweenthelines.szopp.rest.dto.product.ImageDTO;
import pl.betweenthelines.szopp.rest.dto.product.ProductDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDTOFactory {

    @Autowired
    private ImageDTOFactory imageDTOFactory;

    public List<ProductDTO> buildProductDTOs(List<Product> products) {
        return products.stream()
                .map(this::buildProductDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO buildProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryId(getCategory(product))
                .inStock(product.getInStock())
                .images(getImages(product))
                .build();
    }

    private Long getCategory(Product product) {
        Category category = product.getCategory();
        if (category != null) {
            return category.getId();
        }

        return null;
    }

    private List<ImageDTO> getImages(Product product) {
        return imageDTOFactory.buildImageDTOs(product.getImages());
    }
}
