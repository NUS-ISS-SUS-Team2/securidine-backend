package com.SecuriDine.OrderService.Service;

import java.util.List;
import java.util.Optional;

import com.SecuriDine.OrderService.DTO.OrderDTO;
import com.SecuriDine.OrderService.Entity.Order;

import org.springframework.stereotype.Service;

public interface OrderService {
	List<OrderDTO> getAllOrders();
    
    Optional<OrderDTO> getOrderByCustName(String customerName);
    
    void deleteOrder(Long orderId);

	OrderDTO save(OrderDTO OrderDTO);

}

