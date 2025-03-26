package org.product.info.service;

import lombok.extern.slf4j.Slf4j;
import org.product.info.exception.ProductNotFoundException;
import org.product.info.exception.ProductServiceException;
import org.product.info.model.CombinedProductStockDTO;
import org.product.info.model.Inventory;
import org.product.info.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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
        log.info("Retrieving products");
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
        log.info("Retrieving a product with product ID {}", productId);
         return Optional.ofNullable(getAllProducts().stream()
                 .filter(product -> product.productId == productId)
                 .findFirst()
                 .orElseThrow(() -> new ProductNotFoundException("Product with Id" + productId + "not found")));
    }


    @Override
    public CombinedProductStockDTO addProduct(CombinedProductStockDTO product) {
        log.info("Saving new product {}", product);
        try {
            Product createdProduct = restTemplate.postForObject(CATALOG_BASEURL, product, Product.class);
            if(createdProduct == null || createdProduct.getProductId() == 0) {
                throw new ProductServiceException("Failed to create a new Product");
            }
            product.setProductId(createdProduct.productId);
            restTemplate.postForObject(INVENTORY_BASEURL, product, Inventory.class);
            return product;
        } catch (Exception e) {
            throw new ProductServiceException("Failed to create a new Product", e);
        }
    }

    @Override
    public CombinedProductStockDTO updateProduct(CombinedProductStockDTO productDetails) {
        log.info("Updating a product {}", productDetails);
        restTemplate.put(CATALOG_BASEURL + "/" + productDetails.getProductId(), productDetails);
        restTemplate.put(INVENTORY_BASEURL + "/" + productDetails.getProductId(), productDetails);
        return productDetails;
    }

    @Override
    public void deleteProduct(int productId) {
        log.info("Deleting a product with productID {}", productId);
        try {
            restTemplate.delete(CATALOG_BASEURL +"/" + productId);
        } catch (Exception e) {
            throw new ProductServiceException("Failed to delete a product", e);
        }
    }
}
