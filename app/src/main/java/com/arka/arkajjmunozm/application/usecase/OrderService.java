package com.arka.arkajjmunozm.application.usecase;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.arka.arkajjmunozm.domain.model.Order;
import com.arka.arkajjmunozm.domain.port.in.IOrderService;
import com.arka.arkajjmunozm.infraestructure.adapter.in.web.dto.OrderDTO;
import com.arka.arkajjmunozm.infraestructure.adapter.in.web.mapper.OrderMapper;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.OrderEntity;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.ProductEntity;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.repository.OrderRepository;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.repository.ProductRepository;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final ProductService productService;

    @Override
    public List<Order> allOrders(){
        return orderRepository.findAll()
                .stream()
                .map(this::mapToDomain)
                .toList();
    }

    @Override
    public Order newOrder(OrderEntity order) {
        int idUser = order.getUser().getId();
        //recuperar usuario
        UserEntity user = userRepository.findById(idUser).orElse(null);

        //recuperar productos
        List<ProductEntity> products = order.getProducts().stream()
                .map(product -> productRepository.findById(product.getId())
                        .orElseThrow(() -> new RuntimeException("Product not found with id: " + product.getId())))
                .collect(Collectors.toList());

        if(user != null){
            order.setUser(user);
            order.setProducts(products);
            return orderRepository.save(order);
        }else{
            return null;
        }
    }

    @Override
    public Order getOrder(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order updateOrder(int id, OrderEntity order) {
        Optional<OrderEntity> orderExist = orderRepository.findById(id);
        if (orderExist.isPresent()) {
            order.setId(id);
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public boolean deleteOrder(int id) {
        Optional<OrderEntity> orderExist = orderRepository.findById(id);
        if (orderExist.isPresent()) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<OrderDTO> getOrdersByProduct(int id_product) {
        List<OrderEntity> orderEntities = orderRepository.findOrdersByIdProduct(id_product);
        List<Order> orders = orderEntities.stream()
                .map(this::mapToDomain)
                .toList();
        return orderMapper.ordersToOrdersDTO(orders);
    }

    @Override
    public List<OrderDTO> getOrdersByUser(int id_user) {
        List<OrderEntity> orderEntities = orderRepository.findOrdersByUserId(id_user);
        List<Order> orders = orderEntities.stream()
                .map(this::mapToDomain)
                .toList();
        return orderMapper.ordersToOrdersDTO(orders);
    }

    private Order mapToDomain(OrderEntity orderEntity) {
        return new Order(
                orderEntity.getId(),
                orderEntity.getTotalAmount(),
                orderEntity.getDate(),
                orderEntity.isSend()
        );
    }
}
