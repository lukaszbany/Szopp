package pl.betweenthelines.szopp.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.betweenthelines.szopp.domain.Category;
import pl.betweenthelines.szopp.domain.Product;
import pl.betweenthelines.szopp.domain.repository.ProductRepository;
import pl.betweenthelines.szopp.exception.NotFoundException;
import pl.betweenthelines.szopp.rest.dto.product.AddProductDTO;
import pl.betweenthelines.szopp.rest.dto.product.EditProductDTO;
import pl.betweenthelines.szopp.service.category.CategoryService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Transactional
    public Product getProduct(Long id) {
        if (id == null) {
            throw new NotFoundException();
        }

        return productRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public List<Product> getProductsByCategory(Long categoryId) {
        Category category = categoryService.getCategory(categoryId);
        List<Category> categoryAndChildren = getChildrenRecursively(category);

        return productRepository.findAllByCategoryIn(categoryAndChildren);
    }



    private List<Category> getChildrenRecursively(Category category) {
        List<Category> children = new ArrayList<>();
        children.add(category);

        for (Category child : category.getChildCategories()) {
            children.addAll(getChildrenRecursively(child));
        }

        return children;
    }

    @Transactional
    public List<Product> getProducts(String name) {
        if (name != null) {
            return productRepository.findAllByNameContainingIgnoreCase(name);
        }

        return productRepository.findAll();
    }

    @Transactional
    public void addProduct(AddProductDTO addProductDTO) {
        Product product = createProduct(addProductDTO);

        productRepository.save(product);
    }

    private Product createProduct(AddProductDTO addProductDTO) {
        Category category = categoryService.getCategory(addProductDTO.getCategoryId());

        return Product.builder()
                .name(addProductDTO.getName())
                .description(addProductDTO.getDescription())
                .price(addProductDTO.getPrice())
                .category(category)
                .inStock(addProductDTO.getInStock())
                .build();
    }

    @Transactional
    public void editProduct(EditProductDTO editProductDTO) {
        Category category = categoryService.getCategory(editProductDTO.getCategoryId());

        Product product = getProduct(editProductDTO.getId());
        product.setName(editProductDTO.getName());
        product.setDescription(editProductDTO.getDescription());
        product.setCategory(category);
        product.setInStock(editProductDTO.getInStock());
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = getProduct(id);

        productRepository.delete(product);
    }
}
