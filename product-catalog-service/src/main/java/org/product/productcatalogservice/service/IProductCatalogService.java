package org.product.productcatalogservice.service;

import org.product.productcatalogservice.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductCatalogService {
    List<Product> getAll();
    List<Product> getByCategory(String category);
    Optional<Product> getByProductId(int productId);
    Product addProduct(Product product);
    Product updateProduct(int productId, Product productDetails);
    void deleteProduct(int productId);
}
