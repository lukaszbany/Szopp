package pl.betweenthelines.szopp.service.product;

import liquibase.util.file.FilenameUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.betweenthelines.szopp.domain.Product;
import pl.betweenthelines.szopp.domain.ProductImage;
import pl.betweenthelines.szopp.domain.repository.ProductImageRepository;
import pl.betweenthelines.szopp.exception.FileProcessingException;
import pl.betweenthelines.szopp.exception.InvalidImageException;
import pl.betweenthelines.szopp.exception.NoSuchImageException;
import pl.betweenthelines.szopp.exception.TooLargeImageException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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

    @Value("#{'${image.extensions.allowed}'.split(',')}")
    private List<String> allowedFileExtensions;

    @Transactional
    public void addProductImage(Long productId, MultipartFile image, String description) {
        validateImage(image);

        Product product = productService.getProduct(productId);
        String filename = createFilename(productId, image);

        tryToSaveImageFile(image, filename);
        saveImageInDatabase(product, filename, description);
    }

    private void saveImageInDatabase(Product product, String filename, String description) {
        ProductImage productImage = ProductImage.builder()
                .product(product)
                .filename(filename)
                .description(description)
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

        log.info("Saving image file to {}", path.toString());
        Files.write(path, bytes);
    }

    private String createFilename(Long productId, MultipartFile image) {
        String prefix = RandomStringUtils.random(5, false, true);
        return productId + "-" + prefix + "-" + image.getOriginalFilename();
    }

    private void validateImage(MultipartFile image) {
        validateImageNotEmpty(image);
        validateImageExtension(image);
        validateImageSize(image);
    }

    private void validateImageExtension(MultipartFile image) {
        String filename = image.getOriginalFilename();
        String extension = FilenameUtils.getExtension(filename);

        Optional.ofNullable(extension)
                .map(String::toLowerCase)
                .filter(allowedFileExtensions::contains)
                .orElseThrow(InvalidImageException::new);
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

    @Transactional
    public void editProductImageOrder(Long productId, Long imageId, int newOrder) {
        Product product = productService.getProduct(productId);
        ProductImage imageToChange = findImage(imageId, product)
                .orElseThrow(NoSuchImageException::new);
        int previousOrder = imageToChange.getOrder();
        if (previousOrder == newOrder) {
            return;
        }

        int offset = getOffset(newOrder, previousOrder);

        product.getImages()
                .stream()
                .filter(image -> isNotImageToChange(image, imageToChange))
                .forEach(image -> moveImageOrder(image, previousOrder, newOrder, offset));

        imageToChange.setOrder(newOrder);
    }

    private boolean isNotImageToChange(ProductImage image, ProductImage imageToChange) {
        return !imageToChange.getId().equals(image.getId());
    }

    private int getOffset(int newOrder, int previousOrder) {
        if (newOrder > previousOrder) {
            return -1;
        }

        return 1;
    }

    private void moveImageOrder(ProductImage image, int previousOrder, int newOrder, int offset) {
        int imageToMoveOrder = image.getOrder();

        if (shouldBeMoved(imageToMoveOrder, previousOrder, newOrder)) {
            image.setOrder(imageToMoveOrder + offset);
        }
    }

    private boolean shouldBeMoved(int imageToMoveOrder, int previousOrder, int newOrder) {
        return Range
                .between(previousOrder, newOrder)
                .contains(imageToMoveOrder);
    }

    private Optional<ProductImage> findImage(Long imageId, Product product) {
        return product.getImages()
                .stream()
                .filter(image -> hasId(image, imageId))
                .findFirst();
    }

    private boolean hasId(ProductImage image, Long imageId) {
        return imageId.equals(image.getId());
    }

    @Transactional
    public void deleteProductImage(Long productId, Long imageId) {
        Product product = productService.getProduct(productId);
        ProductImage imageToDelete = findImage(imageId, product)
                .orElseThrow(NoSuchImageException::new);

        tryToDeleteFile(imageToDelete);
        deleteFromDatabase(imageToDelete);
    }

    private void tryToDeleteFile(ProductImage imageToDelete) {
        try {
            deleteFile(imageToDelete);
        } catch (IOException e) {
            log.info("Cannot delete file {}", imageToDelete.getFilename());
        }
    }

    private void deleteFile(ProductImage imageToDelete) throws IOException {
        String filename = imageToDelete.getFilename();
        Path path = Paths.get(pictureDirectory + filename);

        log.info("Deleting image {}", path.toString());
        Files.delete(path);
    }

    private void deleteFromDatabase(ProductImage imageToDelete) {
        int deletedImagePreviousOrder = imageToDelete.getOrder();
        productImageRepository.delete(imageToDelete);
        imageToDelete.getProduct()
                .getImages()
                .forEach(image -> moveImageOrder(image, deletedImagePreviousOrder));
    }

    private void moveImageOrder(ProductImage image, int deletedImagePreviousOrder) {
        int imageToMoveOrder = image.getOrder();
        if (imageToMoveOrder > deletedImagePreviousOrder) {
            image.setOrder(imageToMoveOrder - 1);
        }
    }

    @Transactional
    public void deleteProductImages(Product product) {
        product.getImages()
                .forEach(this::deleteImage);
    }

    private void deleteImage(ProductImage image) {
        tryToDeleteFile(image);
        deleteFromDatabase(image);
    }
}
