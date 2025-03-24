package org.product.catalog.model;

import jakarta.persistence.*;
import lombok.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int product_id;

    public String name;
    public String brand;
    public String category;
    public double price;
}
