package com.arka.arkajjmunozm.domain.port.in;

import com.arka.arkajjmunozm.domain.model.Product;
import com.arka.arkajjmunozm.infraestructure.adapter.in.web.dto.OrderDTO;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.ProductEntity;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    Product getProduct(long id);
    Product AddProduct(ProductEntity product);
    Product updateProduct(int id, Product product);
    boolean deleteProduct(int id);
    List<Product> ProductFilterByDescription(String filter);
    List<Product> getProductsOrderByName();
    List<Product> getProductsRangePrice(double min, double max);
    List<Product> getProductsByCategory(int category_id);


}
