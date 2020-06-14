package pl.betweenthelines.szopp.rest.dto.product.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.betweenthelines.szopp.domain.Category;
import pl.betweenthelines.szopp.domain.Product;
import pl.betweenthelines.szopp.exception.NotFoundException;
import pl.betweenthelines.szopp.rest.dto.product.ImageDTO;
import pl.betweenthelines.szopp.rest.dto.product.ProductDTO;
import pl.betweenthelines.szopp.security.AuthenticationService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static pl.betweenthelines.szopp.security.domain.RoleName.ADMIN;
import static pl.betweenthelines.szopp.security.domain.RoleName.STAFF;

@Component
public class ProductDTOFactory {

    @Autowired
    private ImageDTOFactory imageDTOFactory;

    @Autowired
    private AuthenticationService authenticationService;

    public List<ProductDTO> buildProductDTOs(List<Product> products) {
        boolean notActiveVisible = authenticationService.hasAnyRole(ADMIN, STAFF);

        return products.stream()
                .filter(product -> shouldBeVisible(product, notActiveVisible))
                .map(product -> buildProductDTO(product, notActiveVisible))
                .sorted(byId())
                .collect(Collectors.toList());
    }

    private Comparator<ProductDTO> byId() {
        return Comparator.comparing(ProductDTO::getId);
    }

    private boolean shouldBeVisible(Product product, boolean notActiveVisible) {
        return notActiveVisible ||
                isProductActive(product);
    }

    private boolean isProductActive(Product product) {
        return product.isActive() &&
                product.getCategory() != null &&
                isCategoryActive(product.getCategory());
    }

    private boolean isCategoryActive(Category category) {
        return category == null ||
                (category.isActive() && isCategoryActive(category.getParentCategory()));
    }

    public ProductDTO buildProductDTO(Product product) {
        boolean notActiveVisible = authenticationService.hasAnyRole(ADMIN, STAFF);

        return buildProductDTO(product, notActiveVisible);
    }

    public ProductDTO buildProductDTO(Product product, boolean notActiveVisible) {
        if (!shouldBeVisible(product, notActiveVisible)) {
            throw new NotFoundException();
        }

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .shortDescription(product.getShortDescription())
                .price(product.getPrice())
                .categoryId(getCategory(product))
                .inStock(product.getInStock())
                .images(getImages(product))
                .active(product.isActive())
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
