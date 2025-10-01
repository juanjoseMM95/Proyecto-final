package com.arka.arkajjmunozm.infraestructure.adapter.in.web.mapper;

import com.arka.arkajjmunozm.domain.model.Order;
import com.arka.arkajjmunozm.infraestructure.adapter.in.web.dto.OrderDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-30T22:05:18-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public List<Order> ordersDTOToOrders(List<OrderDTO> ordersDTO) {
        if ( ordersDTO == null ) {
            return null;
        }

        List<Order> list = new ArrayList<Order>( ordersDTO.size() );
        for ( OrderDTO orderDTO : ordersDTO ) {
            list.add( orderDTOToOrder( orderDTO ) );
        }

        return list;
    }

    @Override
    public List<OrderDTO> ordersToOrdersDTO(List<Order> orders) {
        if ( orders == null ) {
            return null;
        }

        List<OrderDTO> list = new ArrayList<OrderDTO>( orders.size() );
        for ( Order order : orders ) {
            list.add( orderToOrderDTO( order ) );
        }

        return list;
    }

    @Override
    public Order orderDTOToOrder(OrderDTO orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }

        Order order = new Order();

        order.setDate( orderDTO.getDate() );
        order.setId( orderDTO.getId() );
        order.setTotalAmount( orderDTO.getTotalAmount() );

        return order;
    }

    protected OrderDTO orderToOrderDTO(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setDate( order.getDate() );
        orderDTO.setId( order.getId() );
        orderDTO.setTotalAmount( order.getTotalAmount() );

        return orderDTO;
    }
}
