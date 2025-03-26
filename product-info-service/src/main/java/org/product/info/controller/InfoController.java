package org.product.info.controller;

import lombok.extern.slf4j.Slf4j;
import org.product.info.exception.ProductServiceException;
import org.product.info.model.CombinedProductStockDTO;
import org.product.info.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/info")
public class InfoController {

    private final ProductService service;

    public InfoController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CombinedProductStockDTO>> getAllProducts() {
        log.info("Fetching all Products.");
        List<CombinedProductStockDTO> products = service.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable int productId) {
        log.info("Fetching a product with productId {}", productId);
        try {
            Optional<CombinedProductStockDTO>  product = service.getProductById(productId);
            if (product.isPresent()) {
                return ResponseEntity.ok().body(product.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Product with ID " + productId + " not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the proudct.");
        }
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody CombinedProductStockDTO product) {
        log.info("Adding new Product {}", product);
        try {
            CombinedProductStockDTO savedProduct = service.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to Create a product");
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable int productId, @RequestBody CombinedProductStockDTO productDetails) {
        log.info("Updating a product with productID {}", productId );
        try {
            productDetails.setProductId(productId);
            CombinedProductStockDTO updatedProduct = service.updateProduct(productDetails);
            return ResponseEntity.ok().body(updatedProduct);
        } catch (ProductServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to update product: " + e.getMessage());
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable int productId) {
        log.info("Deleting a Product with ID {}", productId);
        try {
            service.deleteProduct(productId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product with Id: " + productId + " deleted successfully");
        } catch (ProductServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete product: " + e.getMessage());
        }

    }
}
