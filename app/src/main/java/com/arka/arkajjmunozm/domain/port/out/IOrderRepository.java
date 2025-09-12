package com.arka.arkajjmunozm.domain.port.out;

import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.ProductEntity;

import java.util.List;

public interface IOrderRepository {
    List<ProductEntity> findProductsbyShortName(String filter);
    List<ProductEntity> findProductsRangePrice(double min, double max);
    List<ProductEntity> findProductsOrderByName();
    List<ProductEntity> findProductsByCategory(int id);
}
