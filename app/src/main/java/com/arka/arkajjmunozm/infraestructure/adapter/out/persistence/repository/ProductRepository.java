package com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.repository;

import java.util.List;

import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    @Query(name = "Product.findProductsbyShortName")
    List<ProductEntity> findProductsbyShortName(@Param("descp") String filter);
    @Query(name = "Product.findProductsRangePrice")
    List<ProductEntity> findProductsRangePrice(@Param("min") double min, @Param("max") double max);
    @Query(name = "Product.findProductsOrderByName")
    List<ProductEntity> findProductsOrderByName();
    @Query(name = "Product.findProductsByCategory")
    List<ProductEntity> findProductsByCategory(@Param("id_category") int id);
}
