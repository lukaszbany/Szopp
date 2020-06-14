package pl.betweenthelines.szopp.rest.controller;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.betweenthelines.szopp.domain.Product;
import pl.betweenthelines.szopp.rest.dto.product.AddProductDTO;
import pl.betweenthelines.szopp.rest.dto.product.EditProductDTO;
import pl.betweenthelines.szopp.rest.dto.product.ProductDTO;
import pl.betweenthelines.szopp.rest.dto.product.factory.ProductDTOFactory;
import pl.betweenthelines.szopp.rest.success.SuccessResponse;
import pl.betweenthelines.szopp.rest.success.SuccessResponseFactory;
import pl.betweenthelines.szopp.service.product.ProductDeleteService;
import pl.betweenthelines.szopp.service.product.ProductImageService;
import pl.betweenthelines.szopp.service.product.ProductService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ProductEndpoint {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductDeleteService productDeleteService;

    @Autowired
    private ProductDTOFactory productDTOFactory;

    @Autowired
    private SuccessResponseFactory successResponseFactory;

    @RequestMapping(method = GET, value = "/products")
    public List<ProductDTO> getProducts(@RequestParam(required = false) String name) {
        List<Product> products = productService.getProducts(name);

        return productDTOFactory.buildProductDTOs(products);
    }

    @RequestMapping(method = GET, value = "/products/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);

        return productDTOFactory.buildProductDTO(product);
    }

    @RequestMapping(method = GET, value = "categories/{categoryId}/products")
    public List<ProductDTO> getProductsInCategory(@PathVariable Long categoryId) {
        List<Product> productsByCategory = productService.getProductsByCategory(categoryId);

        return productDTOFactory.buildProductDTOs(productsByCategory);
    }

    @RequestMapping(method = POST, value = "/products")
    public ProductDTO addProduct(@RequestBody @Valid AddProductDTO addProductDTO) {
        Product product = productService.addProduct(addProductDTO);

        return productDTOFactory.buildProductDTO(product);
    }

    @RequestMapping(method = PUT, value = "/products")
    public ResponseEntity<SuccessResponse> editProduct(@RequestBody @Valid EditProductDTO editProductDTO) {
        productService.editProduct(editProductDTO);

        return successResponseFactory.buildSuccessResponseEntity("success.product.edit");
    }

    @RequestMapping(method = DELETE, value = "/products/{id}")
    public ResponseEntity<SuccessResponse> deleteProduct(@PathVariable Long id) {
        productDeleteService.deleteProduct(id);

        return successResponseFactory.buildSuccessResponseEntity("success.product.delete");
    }

    @RequestMapping(method = POST, value = "/products/{productId}/image")
    public ResponseEntity<SuccessResponse> uploadProductImage(@PathVariable Long productId, @RequestParam MultipartFile image, @RequestParam String description) {
        productImageService.addProductImage(productId, image, description);

        return successResponseFactory.buildSuccessResponseEntity("success.product.image.add");
    }

    @RequestMapping(method = PUT, value = "/products/{productId}/image/{imageId}")
    public ResponseEntity<SuccessResponse> editProductImageOrder(
            @PathVariable Long productId, @PathVariable Long imageId, @RequestBody @NonNull Integer order) {
        productImageService.editProductImageOrder(productId, imageId, order);

        return successResponseFactory.buildSuccessResponseEntity("success.product.image.edit");
    }

    @RequestMapping(method = DELETE, value = "/products/{productId}/image/{imageId}")
    public ResponseEntity<SuccessResponse> deleteProductImage(
            @PathVariable Long productId, @PathVariable Long imageId) {

        productImageService.deleteProductImage(productId, imageId);

        return successResponseFactory.buildSuccessResponseEntity("success.product.image.delete");
    }

}
