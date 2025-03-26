package org.product.info.service;

import org.product.info.model.CombinedProductStockDTO;
import org.product.info.model.Inventory;
import org.product.info.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductInfoService {
    String CATALOG_BASEURL = "http://PRODUCT-CATALOG/api/v1/catalog/products";
    String INVENTORY_BASEURL = "http://PRODUCT-INVENTORY/api/v1/inventories";

    private final RestTemplate restTemplate;

    public ProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<CombinedProductStockDTO> getAllProducts() {
        List<Product> products = List.of(Objects.requireNonNull(restTemplate.getForObject(CATALOG_BASEURL, Product[].class)));
        List<Inventory> inventories = List.of(Objects.requireNonNull(restTemplate.getForObject(INVENTORY_BASEURL, Inventory[].class)));

        return products.stream()
                .map(product -> {
                    Inventory inventory = inventories.stream()
                            .filter( inv -> product.productId == inv.productId)
                            .findFirst()
                            .orElse(new Inventory(product.productId, 0));
                    return new CombinedProductStockDTO(
                            product.productId,
                            product.name,
                            product.brand,
                            product.category,
                            product.price,
                            inventory.availableQuantity
                    );
                }).collect(Collectors.toList());
    }

    @Override
    public Optional<CombinedProductStockDTO> getProductById(int productId) {
         return getAllProducts().stream().filter(product -> product.productId == productId).findFirst();
    }

    @Override
    public CombinedProductStockDTO addProduct(CombinedProductStockDTO product) {
        Product createdProduct = restTemplate.postForObject(CATALOG_BASEURL, product, Product.class);
        if(createdProduct != null && createdProduct.getProductId() != 0) {
            System.out.println("createdProduct..." + createdProduct);
            product.setProductId(createdProduct.productId);
            restTemplate.postForObject(INVENTORY_BASEURL, product, Inventory.class);
        } else {
            throw new RuntimeException("Failed to create a new Product or product Id not generated");
        }
        return product;
    }

    @Override
    public CombinedProductStockDTO updateProduct(CombinedProductStockDTO productDetails) {
        System.out.println("Product Deatils.." + productDetails);
        restTemplate.put(CATALOG_BASEURL + "/" + productDetails.getProductId(), productDetails);
        restTemplate.put(INVENTORY_BASEURL + "/" + productDetails.getProductId(), productDetails);
        return productDetails;
    }

    @Override
    public void deleteProduct(int productId) {
        restTemplate.delete(CATALOG_BASEURL +"/" + productId);
    }
}
