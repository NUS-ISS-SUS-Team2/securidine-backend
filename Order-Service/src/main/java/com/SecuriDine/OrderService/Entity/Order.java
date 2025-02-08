package com.SecuriDine.OrderService.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.SecuriDine.OrderService.DTO.OrderDTO;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long OrderId;
    
    @Column(name = "customer_name")
    private String customerName;
    
    @Column(name = "delivery_address")
    private String deliveryAddress;
    
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    
    @Column(name = "total_price")
    private Float totalPrice;
    
    public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public Long getOrderId() {
		return OrderId;
	}

	public void setOrderId(Long orderId) {
		OrderId = orderId;
	}

	public Order() {
    }

    public Order(String customerName, String deliveryAddress, LocalDateTime orderDate, Float totalPrice) {
        this.customerName = customerName;
        this.deliveryAddress = deliveryAddress;
    	this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }
    	

    public OrderDTO convertToDTO() {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(this.OrderId);
        dto.setCustomerName(this.customerName);
        dto.setDeliveryAddress(this.deliveryAddress);
        dto.setOrderDate(this.orderDate);
        dto.setTotalPrice(this.totalPrice);
        return dto;
    }

}
