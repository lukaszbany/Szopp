package pl.betweenthelines.szopp.service.product;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.betweenthelines.szopp.domain.Product;
import pl.betweenthelines.szopp.domain.ProductImage;
import pl.betweenthelines.szopp.domain.repository.ProductImageRepository;
import pl.betweenthelines.szopp.exception.FileProcessingException;
import pl.betweenthelines.szopp.exception.InvalidImageException;
import pl.betweenthelines.szopp.exception.TooLargeImageException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductService productService;

    @Value("${product.picture.directory}")
    private String pictureDirectory;

    @Value("${file.upload.max.size}")
    private long maxImageSize;

    @Transactional
    public void addProductImage(Long productId, MultipartFile image) {
        validateImage(image);

        Product product = productService.getProduct(productId);
        String filename = createFilename(productId, image);

        tryToSaveImageFile(image, filename);
        saveImageInDatabase(product, filename);
    }

    private void saveImageInDatabase(Product product, String filename) {
        ProductImage productImage = ProductImage.builder()
                .product(product)
                .filename(filename)
                .order(product.getImages().size())
                .build();

        product.getImages().add(productImage);
        productImageRepository.save(productImage);
    }

    private void tryToSaveImageFile(MultipartFile image, String filename) {
        try {
            saveImage(image, filename);
        } catch (IOException e) {
            log.info("Cannot process file", e);
            throw new FileProcessingException();
        }
    }

    private void saveImage(MultipartFile image, String filename) throws IOException {
        byte[] bytes = image.getBytes();
        Path path = Paths.get(pictureDirectory + filename);
        Files.write(path, bytes);
    }

    private String createFilename(Long productId, MultipartFile image) {
        String prefix = RandomStringUtils.random(5, false, true);
        return productId + "-" + prefix + "-" + image.getOriginalFilename();
    }

    private void validateImage(MultipartFile image) {
        validateImageNotEmpty(image);
        validateImageSize(image);
    }

    private void validateImageNotEmpty(MultipartFile image) {
        if (image.isEmpty()) {
            throw new InvalidImageException();
        }
    }

    private void validateImageSize(MultipartFile image) {
        if (image.getSize() > maxImageSize) {
            throw new TooLargeImageException();
        }
    }
}
