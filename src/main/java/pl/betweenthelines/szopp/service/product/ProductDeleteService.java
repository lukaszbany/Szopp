package pl.betweenthelines.szopp.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Product;
import pl.betweenthelines.szopp.exception.ProductHasOrderException;

import javax.transaction.Transactional;

@Service
public class ProductDeleteService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productService.getProduct(productId);
        validateProductHasNoOrder(product);

        productImageService.deleteProductImages(product);
        productService.deleteProduct(product);
    }

    private void validateProductHasNoOrder(Product product) {
        if (productService.hasOrders(product)) {
            throw new ProductHasOrderException();
        }
    }
}
