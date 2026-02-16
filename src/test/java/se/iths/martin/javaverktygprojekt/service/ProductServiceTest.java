package se.iths.martin.javaverktygprojekt.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.martin.javaverktygprojekt.exceptions.ProductNotFoundException;
import se.iths.martin.javaverktygprojekt.exceptions.ProductValidationException;
import se.iths.martin.javaverktygprojekt.model.Product;
import se.iths.martin.javaverktygprojekt.repository.ProductRepository;
import se.iths.martin.javaverktygprojekt.validator.ProductValidator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductValidator productValidator;


    @Test
    @DisplayName("should return all products")
    void shouldGetAllProducts() {
        List<Product> mockProducts = List.of(
                new Product("Product 1", 10.0, "Description 1"),
                new Product("Product 2", 20.0, "Description 2")
        );

        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("should return product by id when product exists")
    void shouldGetProductById() {
        Product mockProduct = new Product("Product 1", 10.0, "Description 1");

        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

        Product result = productService.getProductById(1L);

        assertEquals(mockProduct, result);
    }

    @Test
    @DisplayName("should throw ProductNotFoundException when product does not exist")
    void shouldThrowWhenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.getProductById(1L)
        );
    }


    @Test
    @DisplayName("should save product when product is valid")
    void shouldCreateProduct() {
        Product mockProduct = new Product("Product 1", 10.0, "Description 1");

        when(productRepository.save(mockProduct)).thenReturn(mockProduct);

        Product result = productService.createProduct(mockProduct);

        assertEquals(mockProduct, result);

        verify(productValidator).validate(mockProduct);
    }

    @Test
    @DisplayName("should throw ProductValidationException when product is invalid")
    void shouldThrowWhenInvalidProduct() {
        Product invalidProduct = new Product("", -5.0, "");

        doThrow(new ProductValidationException("Invalid product"))
                .when(productValidator).validate(invalidProduct);

        assertThrows(
                ProductValidationException.class,
                () -> productService.createProduct(invalidProduct)
        );

        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("should update product when product exists and valid")
    void shouldUpdateProduct() {
        Product existingProduct = new Product("Old name", 10.0, "Old desc");
        Product updatedInfo = new Product("New name", 15.0, "New desc");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        Product result = productService.updateProduct(1L, updatedInfo);

        assertEquals("New name", result.getName());
        assertEquals(15.0, result.getPrice());
        assertEquals("New desc", result.getDescription());
    }


    @Test
    @DisplayName("should throw ProductNotFoundException when updating non-existing product")
    void shouldThrowWhenUpdatingNonExistingProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(1L, new Product("New", 15.0, "Desc")));

        verify(productRepository, never()).save(any());
    }


    @Test
    @DisplayName("should throw ProductValidationException when updating with invalid product")
    void shouldThrowWhenUpdatingWithInvalidProduct() {
        Product existingProduct = new Product("Old name", 10.0, "Old desc");
        Product invalidUpdatedInfo = new Product("", -5.0, "");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));

        doThrow(new ProductValidationException("Invalid product"))
                .when(productValidator).validate(invalidUpdatedInfo);

        assertThrows(ProductValidationException.class, () ->
                productService.updateProduct(1L, invalidUpdatedInfo));

        verify(productRepository, never()).save(any());
    }


    @Test
    @DisplayName("should delete product when product exists")
    void shouldDeleteProduct() {
        Product existingProduct = new Product("Product 1", 10.0, "Description...");
        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));

        productService.deleteProduct(1L);

        verify(productRepository).delete(existingProduct);
    }


    @Test
    @DisplayName("should throw ProductNotFoundException when deleting non-existing product")
    void shouldThrowWhenDeletingNonExistingProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1L));

        verify(productRepository, never()).delete(any());
    }


}