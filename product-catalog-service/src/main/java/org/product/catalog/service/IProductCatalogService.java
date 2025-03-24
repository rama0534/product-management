package org.product.catalog.service;

import org.product.catalog.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductCatalogService {
    List<Product> getAll();
    List<Product> findProductsByCategory(String category);
    Optional<Product> getByProductId(int productId);
    Product addProduct(Product product);
    Product updateProduct(int productId, Product productDetails);
    void deleteProduct(int productId);
}
