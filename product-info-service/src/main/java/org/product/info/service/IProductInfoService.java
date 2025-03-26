package org.product.info.service;

import org.product.info.model.CombinedProductStockDTO;

import java.util.List;
import java.util.Optional;

public interface IProductInfoService {
    List<CombinedProductStockDTO> getAllProducts();
    Optional<CombinedProductStockDTO> getProductById(int productId);
    CombinedProductStockDTO addProduct(CombinedProductStockDTO inventory);
    CombinedProductStockDTO updateProduct(CombinedProductStockDTO inventoryDetails);
    void deleteProduct(int productId);
}
