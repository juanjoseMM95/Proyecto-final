package com.arka.arkajjmunozm.infraestructure.adapter.in.web;

import com.arka.arkajjmunozm.application.usecase.ProductService;
import com.arka.arkajjmunozm.domain.model.Product;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.ProductEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor

public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProductId(@PathVariable int id) {
        Product product = productService.getProduct(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> AddProduct(@Valid @RequestBody ProductEntity product) {
        Product productCreated = productService.AddProduct(product);
        return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") int urlVar, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(urlVar, product);
        if (updatedProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Product> DeleteProduct(@PathVariable int id) {
        boolean deleteProduct = productService.deleteProduct(id);
        if (!deleteProduct) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Crea una ruta para buscar y devolver una lista de productos cuyo nombre o
    // descripción contengan un término específico
    @GetMapping("filter/{desc}")
    public ResponseEntity<List<Product>> getProductFilterByDesc(@PathVariable String desc) {
        List<Product> products = productService.ProductFilterByDescription(desc);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Define una ruta que te devuelva la lista de todos los productos ordenados
    // alfabéticamente
    @GetMapping("/orderByName")
    public ResponseEntity<List<Product>> getProductsOrderByName() {
        List<Product> products = productService.getProductsOrderByName();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //Crea una ruta para buscar y devolver una lista de productos cuyos precios se encuentre en un rango dado por la petición
    @GetMapping("rangeprice")
    public ResponseEntity<List<Product>> getProductsRangePrice(@RequestParam("min_price") double min, @RequestParam("max_price") double max) {
        List<Product> products = productService.getProductsRangePrice(min, max);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //Búsqueda de productos por categoría
    @GetMapping("category/{id}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable int id) {
        List<Product> products = productService.getProductsByCategory(id);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
