package com.arka.arkajjmunozm.application.usecase;

import java.util.List;
import java.util.Optional;

import com.arka.arkajjmunozm.domain.model.Product;
import com.arka.arkajjmunozm.domain.port.in.IProductService;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.ProductEntity;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.repository.ProductRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDomain)
                .toList();
    }

    @Override
    public Product getProduct(int id) {
        return productRepository.findById(id)
                .map(this::mapToDomain)
                .orElse(null);
    }

    @Override
    public Product AddProduct(ProductEntity product) {
        ProductEntity savedEntity = productRepository.save(product);
        return mapToDomain(savedEntity);
    }

    @Override
    public Product updateProduct(int id, Product product) {
        Optional<ProductEntity> productExist = productRepository.findById(id);
        if (productExist.isPresent()) {
            ProductEntity productEntity = productExist.get();
            // Update the entity fields
            productEntity.setDescription(product.getDescription());
            productEntity.setValue(product.getValue());
            productEntity.setMax_discount(product.getMax_discount());
            productEntity.setAvalible(product.isAvalible());
            ProductEntity savedEntity = productRepository.save(productEntity);
            return mapToDomain(savedEntity);
        }
        return null;
    }

    @Override
    public boolean deleteProduct(int id) {
        Optional<ProductEntity> productExist = productRepository.findById(id);
        if (productExist.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Product> ProductFilterByDescription(String filter) {
        String fixFilter = "%" + filter + "%";
        return productRepository.findProductsbyShortName(fixFilter)
                .stream()
                .map(this::mapToDomain)
                .toList();
    }

    @Override
    public List<Product> getProductsOrderByName() {
        return productRepository.findProductsOrderByName()
                .stream()
                .map(this::mapToDomain)
                .toList();
    }

    @Override
    public List<Product> getProductsRangePrice(double min, double max) {
        return productRepository.findProductsRangePrice(min, max)
                .stream()
                .map(this::mapToDomain)
                .toList();
    }

    @Override
    public List<Product> getProductsByCategory(int id_category) {
        return productRepository.findProductsByCategory(id_category)
                .stream()
                .map(this::mapToDomain)
                .toList();
    }

    private Product mapToDomain(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getDescription(),
                productEntity.getValue(),
                productEntity.getMax_discount(),
                productEntity.isAvalible()
        );
    }

    private ProductEntity mapToEntity(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setDescription(product.getDescription());
        entity.setValue(product.getValue());
        entity.setMax_discount(product.getMax_discount());
        entity.setAvalible(product.isAvalible());
        return entity;
    }
}