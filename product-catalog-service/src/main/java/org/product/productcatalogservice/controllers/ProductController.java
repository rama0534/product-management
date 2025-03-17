package org.product.productcatalogservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.product.productcatalogservice.model.Product;
import org.product.productcatalogservice.service.ProductCatalogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/catalog")
@Tag(name = "Product API", description = "Operations related to Products")
public class ProductController {

    @Autowired
    private ProductCatalogServiceImpl service;

    @GetMapping("/products")
    @Operation(summary = "Get all products", description = "Retrieves all available products")
    public List<Product> getAll() {
        return service.getAll();
    }

    @GetMapping("/products/category/{category}")
    @Operation(summary = "Get all products by category", description = "Filter all available products by category")
    public List<Product> getByCategory(@PathVariable String category) {
        return service.getByCategory(category);
    }

    @GetMapping("/products/{productId}")
    @Operation(summary = "Get product by ID", description = "Fetches a product based on its ID")
    public Optional<Product>  getById(@PathVariable int productId) {
        return service.getByProductId(productId);
    }

    @PostMapping("/products")
    @Operation(summary = "Create a product", description = "Create a product")
    public Product createProduct(@RequestBody Product product) {
        return service.addProduct(product);
    }

    @PutMapping("/products/{productId}")
    @Operation(summary = "Update a product", description = "Update product details with it's ID")
    public Product updateProduct(@RequestBody Product productDetails, @PathVariable int productId) {
        return service.updateProduct(productId, productDetails);
    }

    @DeleteMapping("/products/{productId}")
    @Operation(summary = "Delete a product", description = "Delete a product details with it's ID")
    public void deleteProduct(@PathVariable int productId) {
         service.deleteProduct(productId);
    }

}
