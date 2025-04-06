package com.SecuriDine.OrderService.Service;

import com.SecuriDine.OrderService.DTO.OrderDTO;
import com.SecuriDine.OrderService.Entity.Order;
import com.SecuriDine.OrderService.Repository.OrderRepository;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class OrderServiceImpl implements OrderService {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	
    private OrderRepository OrderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository OrderRepository) {
    	this.OrderRepository = OrderRepository;
    }
    
    @Override
    public List<OrderDTO> getAllOrders() {
    	LOGGER.info("LOG FOR BZ - In GET ALL Order");
    	Stream<Order> Orderstream = StreamSupport.stream(OrderRepository
    								.findAll().spliterator(), false)
					                .filter(order -> {
					                    try {
					                        boolean isValid = order.verifyHMAC();
					                        if (!isValid) {
					                            LOGGER.warn("HMAC verification failed for order ID: " + order.getOrderId());
					                        }
					                        return isValid;
					                    } catch (Exception e) {
					                        LOGGER.error("Error verifying HMAC for order ID: " + order.getOrderId(), e);
					                        return false;  // Exclude corrupted orders
					                    }
					                });
        
    	//to log stream 
    	//List<Order> list = OrderRepository.findAll();
    	//System.out.println("Contents of the get all Order: " + list);
    	
    	return Orderstream
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Order> getOrderById(Long orderId) throws Exception {
        Optional<Order> orderOptional = OrderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            if (!order.verifyHMAC()) {
                throw new SecurityException("Data integrity check failed! HMAC mismatch.");
            }
            return Optional.of(order);
        }
        return Optional.empty();
    }

    @Override
    public Optional<OrderDTO> getOrderByCustName(String customerName) {
        Optional<Order> OrderEntityOptional = OrderRepository.findByCustName(customerName);
        return OrderEntityOptional.map(Order::convertToDTO);
    }

    @Override
    public OrderDTO save(OrderDTO OrderDTO) {
    	Order OrderEntity = new Order();
        try {
			OrderEntity.setCustomerName(OrderDTO.getCustomerName());
			OrderEntity.setDeliveryAddress(OrderDTO.getDeliveryAddress());
	        OrderEntity.setOrderDate(OrderDTO.getOrderDate());
	        OrderEntity.setTotalPrice(OrderDTO.getTotalPrice());
	        OrderEntity.computeHMAC();
		} catch (Exception e) {
			e.printStackTrace();
		}
        Order savedEntity = OrderRepository.save(OrderEntity);
        return savedEntity.convertToDTO();
    }

    @Override
    public void deleteOrder(Long OrderId) {
        OrderRepository.deleteById(OrderId);
    }

    public OrderDTO convertToDTO(Order Order) {
    	OrderDTO dto = new OrderDTO();
        dto.setOrderId(Order.getOrderId());
        try {
			dto.setCustomerName(Order.getCustomerName());
			dto.setDeliveryAddress(Order.getDeliveryAddress());
	        dto.setOrderDate(Order.getOrderDate());
	        dto.setTotalPrice(Order.getTotalPrice());
		} catch (Exception e) {
			e.printStackTrace();
		}
        return dto;
    }


    

    


    

}
