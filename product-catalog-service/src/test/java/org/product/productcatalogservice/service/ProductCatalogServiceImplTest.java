package org.product.productcatalogservice.service;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.product.productcatalogservice.model.Product;

import java.util.List;
import java.util.Optional;


class ProductCatalogServiceImplTest {
    @InjectMocks
    private ProductCatalogServiceImpl productCatalogService;

    @Mock
    private ProductRepository productRepository;

    public ProductCatalogServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        List<Product> mockProducts = List.of(new Product(1, "Laptop", "Electronics", "BrandA", 1500.0));
        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> result = productCatalogService.getAll();

        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getName());
    }

    @Test
    public void testGetProductByIdSuccess() {
        Product product = new Product(1, "Phone", "Electronics", "BrandB", 700.0);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Optional<Product> result = productCatalogService.getByProductId(1);

        assertTrue(result.isPresent());
        assertEquals("Phone", result.get().getName());
    }

    @Test
    public void testGetProductByIdNotFound() {

        when(productRepository.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productCatalogService.getByProductId(2);
        });

        assertEquals("Product not found.", exception.getMessage());
    }

    @Test
    public void testAddProduct() {
        Product newProduct = new Product(1, "Tablet", "Electronics", "BrandC", 500.0);
        when(productRepository.save(newProduct)).thenReturn(newProduct);

        Product result = productCatalogService.addProduct(newProduct);

        assertNotNull(result);
        assertEquals("Tablet", result.getName());
    }

    @Test
    public void testUpdateProductSuccess() {
        Product existingProduct = new Product(1, "TV", "Electronics", "BrandD", 800.0);
        Product updatedProduct = new Product(1, "Smart TV", "Electronics", "BrandD", 900.0);

        when(productRepository.findById(1)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productCatalogService.updateProduct(1, updatedProduct);

        assertNotNull(result);
        assertEquals("Smart TV", result.getName());
        assertEquals(900.0, result.getPrice());
    }

    @Test
    public void testUpdateProductNotFound() {
        Product updatedProduct = new Product(1, "Smart TV", "Electronics", "BrandD", 900.0);
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productCatalogService.updateProduct(1, updatedProduct);
        });

        assertEquals("Product not found.", exception.getMessage());
    }

    @Test
    public void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(1);
        assertDoesNotThrow(() -> productCatalogService.deleteProduct(1));
        verify(productRepository, times(1)).deleteById(1);
    }

}