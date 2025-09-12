package com.arka.arkajjmunozm.domain.port.in;

import com.arka.arkajjmunozm.domain.model.Order;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.OrderEntity;
import com.arka.arkajjmunozm.infraestructure.adapter.in.web.dto.OrderDTO;

import java.util.List;

public interface IOrderService {
    List<Order> allOrders();
    Order newOrder(OrderEntity order);
    Order getOrder(int id);
    Order updateOrder(int id, OrderEntity order);
    boolean deleteOrder(int id);
    List<OrderDTO> getOrdersByProduct(int id_product);
    List<OrderDTO> getOrdersByUser(int id_user);
}

