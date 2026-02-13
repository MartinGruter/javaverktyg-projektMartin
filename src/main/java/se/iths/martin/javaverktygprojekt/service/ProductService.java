package se.iths.martin.javaverktygprojekt.service;

import org.springframework.stereotype.Service;
import se.iths.martin.javaverktygprojekt.exceptions.ProductNotFoundException;
import se.iths.martin.javaverktygprojekt.model.Product;
import se.iths.martin.javaverktygprojekt.repository.ProductRepository;
import se.iths.martin.javaverktygprojekt.validator.ProductValidator;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductValidator productValidator;

    public ProductService(ProductRepository productRepository, ProductValidator productValidator) {
        this.productRepository = productRepository;
        this.productValidator = productValidator;
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Product with id " + id + " not found"));
    }


    public Product createProduct(Product product) {
        productValidator.validate(product);
        return productRepository.save(product);
    }


    public Product updateProduct(Long id, Product product) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));

        productValidator.validate(product);

        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setDescription(product.getDescription());

        return productRepository.save(existing);
    }


    public void deleteProduct(Long id) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        productRepository.delete(existing);
    }


}
