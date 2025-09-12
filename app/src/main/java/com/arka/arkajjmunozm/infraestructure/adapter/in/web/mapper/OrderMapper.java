package com.arka.arkajjmunozm.infraestructure.adapter.in.web.mapper;

import com.arka.arkajjmunozm.domain.model.Order;
import com.arka.arkajjmunozm.domain.model.Product;
import com.arka.arkajjmunozm.infraestructure.adapter.in.web.dto.OrderDTO;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel="spring")
public interface OrderMapper {

    /*
    @Mapping(target="userName",source="user.name")
    @Mapping(target="productsId",source="products",qualifiedByName = "mapProductsToIds")
    @Mapping(target = "expirationDate", ignore = true)
    OrderDTO orderToOrderDTO(OrderEntity order);*/

    List<Order> ordersDTOToOrders(List<OrderDTO> ordersDTO);

    List<OrderDTO> ordersToOrdersDTO(List<Order> orders);

    @Named("mapProductsToIds")
    default List<Integer> mapProductsToIds(List<Product> products) {
        if (products == null){
            return List.of();
        }
        return products.stream()
                .map(Product::getId) // Asumiendo que Product tiene un método getId()
                .collect(Collectors.toList());
    }
    Order orderDTOToOrder(OrderDTO orderDTO);
}
