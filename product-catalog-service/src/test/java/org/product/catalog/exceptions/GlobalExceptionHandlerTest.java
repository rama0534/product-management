package org.product.catalog.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    public void testHandleProductNotFoundException() {
        ProductNotFoundException exception = new ProductNotFoundException("Product not found.");
        ResponseEntity<String> response = handler.handleProductNotFoundException(exception);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Product not found.", response.getBody());
    }

    @Test
    public void testHandleGeneralException() {
        Exception exception = new Exception("Unexpected error occurred.");
        ResponseEntity<String> response = handler.handleGeneralException(exception);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("An unexpected error occurred: Unexpected error occurred.", response.getBody());
    }
}