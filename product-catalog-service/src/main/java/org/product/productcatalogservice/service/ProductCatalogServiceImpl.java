package org.product.productcatalogservice.service;

import lombok.extern.slf4j.Slf4j;
import org.product.productcatalogservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductCatalogServiceImpl implements IProductCatalogService {
    private final ProductRepository productRepository;

    public ProductCatalogServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        log.info("Fetching all products.");
        return productRepository.findAll();
    }

    @Override
    public List<Product> findProductsByCategory(String category) {
        log.info("Fetching products by category: {}", category);
        return productRepository.findByCategory(category);
    }

    @Override
    public Optional<Product> getByProductId(int productId) {
        log.info("Fetching product by ID: {}", productId);
        return Optional.ofNullable(productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found.")));
    }

    @Override
    public Product addProduct(Product product) {
        log.info("Adding new product: {}", product.getName());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(int productId, Product productDetails) {
        log.info("Updating product with ID: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found."));

        if (productDetails.getName() != null) product.setName(productDetails.getName());
        if (productDetails.getCategory() != null) product.setCategory(productDetails.getCategory());
        if (productDetails.getBrand() != null) product.setBrand(productDetails.getBrand());
        if (productDetails.getPrice() != 0) product.setPrice(productDetails.getPrice());

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(int productId) {
        log.info("Deleting product with ID: {}", productId);
        productRepository.deleteById(productId);
    }
}
