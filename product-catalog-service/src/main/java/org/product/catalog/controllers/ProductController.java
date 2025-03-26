package org.product.catalog.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.product.catalog.model.Product;
import org.product.catalog.service.ProductCatalogServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@Tag(name = "Product API", description = "Operations related to Products")
public class ProductController {

    private final ProductCatalogServiceImpl service;

    public ProductController(ProductCatalogServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/products")
    @Operation(summary = "Retrieve all products", description = "Fetches a list of all available products.")
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = service.getAll();
        System.out.println("Products from catalog " + products);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/category/{category}")
    @Operation(summary = "Retrieve products by category", description = "Fetches products belonging to the specified category.")
    public ResponseEntity<List<Product>> findProductsByCategory(@PathVariable String category) {
        List<Product> products = service.findProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{productId}")
    @Operation(summary = "Retrieve product by ID", description = "Fetches a single product based on its unique identifier (ID).")
    public ResponseEntity<Product> getById(@PathVariable int productId) {
        return service.getByProductId(productId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/products")
    @Operation(summary = "Create a new product", description = "Adds a new product to the catalog.")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = service.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/products/{productId}")
    @Operation(summary = "Update product details", description = "Updates existing product information using the provided product ID.")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product productDetails, @PathVariable int productId) {
        Product updatedProduct = service.updateProduct(productId, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    @Operation(summary = "Delete product by ID", description = "Removes a product from the catalog using its unique identifier.")
    public ResponseEntity<Void> deleteProduct(@PathVariable int productId) {
        service.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}

