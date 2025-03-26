package org.product.info.controller;

import lombok.extern.slf4j.Slf4j;
import org.product.info.model.CombinedProductStockDTO;
import org.product.info.model.Product;
import org.product.info.service.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
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
        List<CombinedProductStockDTO> products = service.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<CombinedProductStockDTO> getProductById(@PathVariable int productId) {
      Optional<CombinedProductStockDTO>  product = service.getProductById(productId);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CombinedProductStockDTO> addProduct(@RequestBody CombinedProductStockDTO product) {
        CombinedProductStockDTO savedProduct = service.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<CombinedProductStockDTO> updateProduct(@PathVariable int productId, @RequestBody CombinedProductStockDTO productDetails) {
        try {
            productDetails.setProductId(productId);
            CombinedProductStockDTO updatedProduct = service.updateProduct(productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product Details..");
        }

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int productId) {
        service.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}
