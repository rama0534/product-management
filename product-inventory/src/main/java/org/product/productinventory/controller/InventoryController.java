package org.product.productinventory.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.product.productinventory.model.Inventory;
import org.product.productinventory.service.InventoryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/inventories")
@Tag(name = "Inventory API", description = "Operations related to inventories")
public class InventoryController {

    private final InventoryServiceImpl service;

    public InventoryController(InventoryServiceImpl service) {
        this.service = service;
    }

    @GetMapping("")
    @Operation(summary = "Retrieve all inventories", description = "Fetches a list of all available inventories.")
    public ResponseEntity<List<Inventory>> getAllInventories() {
        List<Inventory> inventories = service.getAllInventory();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Retrieve inventory by Product ID", description = "Fetches a single inventory based on Product ID.")
    public ResponseEntity<?> getInventoryById(@PathVariable int productId) {
        return service.getInventoryById(productId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.error("Inventory not found for Product ID: {}", productId);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inventory not found for Product ID: " + productId);
                });
    }

    @PostMapping("")
    @Operation(summary = "Create a new inventory", description = "Adds a new inventory to the Inventories.")
    public ResponseEntity<?> createInventory(@Valid @RequestBody Inventory inventory) {
        try {
            System.out.println(inventory);
            Inventory createdInventory = service.addInventory(inventory);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdInventory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{productId}")
    @Operation(summary = "Update inventory details", description = "Updates existing inventory information.")
    public ResponseEntity<?> updateInventory(@PathVariable int productId, @Valid @RequestBody Inventory inventoryDetails) {
        try {
            inventoryDetails.setProductId(productId);
            Inventory updatedInventory = service.updateInventory(inventoryDetails);
            return ResponseEntity.ok(updatedInventory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete inventory by Product ID", description = "Deletes an inventory.")
    public ResponseEntity<?> deleteInventory(@PathVariable int productId) {
        try {
            service.deleteInventory(productId);
            return ResponseEntity.ok("Inventory deleted successfully for Product ID: " + productId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

}
