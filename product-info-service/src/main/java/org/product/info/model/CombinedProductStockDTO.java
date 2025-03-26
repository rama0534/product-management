package org.product.info.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombinedProductStockDTO {
    public int productId;
    public String name;
    public String brand;
    public String category;
    public double price;
    public int availableQuantity;
}
