package org.product.inventory.service;

import org.product.inventory.model.Inventory;

import java.util.List;
import java.util.Optional;

public interface IInventoryService {
    List<Inventory> getAllInventory();
    Optional<Inventory> getInventoryById(int productId);
    Inventory addInventory(Inventory inventory);
    Inventory updateInventory(Inventory inventoryDetails);
    void deleteInventory(int productId);
}
