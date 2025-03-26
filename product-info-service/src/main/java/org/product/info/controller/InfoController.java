package org.product.info.controller;

import lombok.extern.slf4j.Slf4j;
import org.product.info.exception.InvalidProductException;
import org.product.info.exception.ProductNotFoundException;
import org.product.info.exception.ProductServiceException;
import org.product.info.model.CombinedProductStockDTO;
import org.product.info.service.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/info")
public class InfoController {

    private final ProductServiceImpl service;

    public InfoController(ProductServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CombinedProductStockDTO>> getAllProducts() {
        log.info("Fetching all Products.");
        List<CombinedProductStockDTO> products = service.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<CombinedProductStockDTO> getProductById(@PathVariable int productId) {
        log.info("Fetching a product with productId {}", productId);
        Optional<CombinedProductStockDTO>  product = service.getProductById(productId);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CombinedProductStockDTO> createProduct(@RequestBody CombinedProductStockDTO product) {
        log.info("Adding new Product {}", product);
        CombinedProductStockDTO savedProduct = service.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<CombinedProductStockDTO> updateProduct(@PathVariable int productId, @RequestBody CombinedProductStockDTO productDetails) {
        log.info("Updating a product with productID {}", productId );
        try {
            productDetails.setProductId(productId);
            CombinedProductStockDTO updatedProduct = service.updateProduct(productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (ProductNotFoundException | InvalidProductException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ProductServiceException("Failed to update product Details..", e);
        }

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int productId) {
        log.info("Deleting a Product with ID {}", productId);
        service.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}
