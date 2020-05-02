package pl.betweenthelines.szopp.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.betweenthelines.szopp.domain.Product;
import pl.betweenthelines.szopp.rest.dto.product.AddProductDTO;
import pl.betweenthelines.szopp.rest.dto.product.EditProductDTO;
import pl.betweenthelines.szopp.rest.dto.product.ProductDTO;
import pl.betweenthelines.szopp.rest.dto.product.factory.ProductDTOFactory;
import pl.betweenthelines.szopp.service.product.ProductImageService;
import pl.betweenthelines.szopp.service.product.ProductService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ProductEndpoint {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductDTOFactory productDTOFactory;

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
    public String addProduct(@RequestBody @Valid AddProductDTO addProductDTO) {
        productService.addProduct(addProductDTO);

        return "OK";
    }

    @RequestMapping(method = PUT, value = "/products")
    public ResponseEntity<String> editProduct(@RequestBody @Valid EditProductDTO editProductDTO) {
        productService.editProduct(editProductDTO);

        return ResponseEntity.ok("Success");
    }

    @RequestMapping(method = DELETE, value = "/products/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        return "OK";
    }

    @RequestMapping(method = POST, value = "/products/{productId}/image")
    public String uploadProductImage(@PathVariable Long productId, @RequestParam MultipartFile image) {
        productImageService.addProductImage(productId, image);

        return "OK";
    }

}
