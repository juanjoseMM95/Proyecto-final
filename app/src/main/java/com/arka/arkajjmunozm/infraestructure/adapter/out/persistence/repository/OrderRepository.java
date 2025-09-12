package com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.repository;

import java.util.List;

import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    @Query(name = "Order.findOrdersByIdProduct")
    List<OrderEntity> findOrdersByIdProduct(@Param("id_product") int id);
    @Query(name = "User.findOrdersByUserId")
    List<OrderEntity> findOrdersByUserId(@Param("id_user") int id);
}
