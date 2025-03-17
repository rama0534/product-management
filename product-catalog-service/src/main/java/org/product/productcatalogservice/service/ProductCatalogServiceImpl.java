package org.product.productcatalogservice.service;

import org.product.productcatalogservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCatalogServiceImpl implements IProductCatalogService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getByCategory(String category) {
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
        return productRepository.findById(productId)
                .map(product -> {
                    product.setName(productDetails.name);
                    product.setCategory(productDetails.category);
                    product.setBrand(productDetails.brand);
                    product.setPrice(productDetails.price);
                    return productRepository.save(product);
                }).orElseThrow(() -> new RuntimeException("Product not found."));
    }

    @Override
    public void deleteProduct(int productId) {
        productRepository.deleteById(productId);
    }
}
