package org.product.inventory.service;

import lombok.extern.slf4j.Slf4j;
import org.product.inventory.model.Inventory;
import org.product.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InventoryServiceImpl implements IInventoryService {

    private final InventoryRepository repository;

    public InventoryServiceImpl(InventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Inventory> getAllInventory() {
        log.info("Fetching all inventories.");
        return repository.getAll();
    }

    @Override
    public Optional<Inventory> getInventoryById(int productId) {
        log.info("Fetching an inventory by product ID. {}", productId);
        return  repository.findById(productId)
                .or(() -> {
                    log.warn("Inventory not found for ProductID: {}", productId);
                    return Optional.empty();
                });
    }

    @Override
    public Inventory addInventory(Inventory inventory) {
        log.info("Adding new inventory.{}", inventory);
        int result = repository.save(inventory);
        if(result > 0) {
            return inventory;
        } else {
            throw new RuntimeException("Failed to add inventory.");
        }
    }



    @Override
    public Inventory updateInventory(Inventory inventoryDetails) {
        log.info("Updating inventory with productId {}", inventoryDetails.getProductId());
         return repository.findById(inventoryDetails.getProductId())
                 .map(inventory -> {
                     inventory.setAvailableQuantity((inventoryDetails.getAvailableQuantity()));
                     if(repository.update(inventory) > 0) return inventory;
                     throw new RuntimeException("Failed to update inventory");
                 }).orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    @Override
    public void deleteInventory(int productId) {
        log.info("deleting inventory with productId {}", productId);
        int result = repository.delete(productId);
        if(result < 0)
            throw new RuntimeException("Failed to delete inventory." + productId);
    }
}
