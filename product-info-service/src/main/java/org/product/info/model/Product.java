package org.product.info.model;

import lombok.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    public int productId;
    public String name;
    public String brand;
    public String category;
    public double price;
}

