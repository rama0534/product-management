package org.product.productinventory.service;

import org.product.productinventory.model.Inventory;

import java.util.List;
import java.util.Optional;

public interface IInventoryService {
    List<Inventory> getAllInventory();
    Optional<Inventory> getInventoryById(int productId);
    Inventory addInventory(Inventory inventory);
    Inventory updateInventory(Inventory inventoryDetails);
    void deleteInventory(int productId);
}
