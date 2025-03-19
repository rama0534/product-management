package org.product.productcatalogservice.service;

import org.product.productcatalogservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCatalogServiceImpl implements IProductCatalogService {
    private final ProductRepository productRepository;

    public ProductCatalogServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Optional<Product> getByProductId(int productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(int productId, Product productDetails) {
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
        productRepository.deleteById(productId);
    }
}
