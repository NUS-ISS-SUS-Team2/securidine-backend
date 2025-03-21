package com.SecuriDine.OrderService.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.SecuriDine.OrderService.DTO.OrderDTO;
import com.SecuriDine.OrderService.Util.AESUtil;
import com.SecuriDine.OrderService.Util.HMACUtil;

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
    
    @Column(name = "hmac_string", nullable = false)
    private String hmac;

	public void computeHMAC() throws Exception {
        String data = customerName + deliveryAddress + totalPrice;
        this.hmac = HMACUtil.generateHMAC(data);
    }

    public boolean verifyHMAC() throws Exception {
        String data = customerName + deliveryAddress + totalPrice;
        return HMACUtil.verifyHMAC(data, this.hmac);
    }

    
    public String getCustomerName() throws Exception {
		return AESUtil.decrypt(customerName);
	}

	public void setCustomerName(String customerName) throws Exception {
		this.customerName = AESUtil.encrypt(customerName);
	}

	public String getDeliveryAddress() throws Exception {
		return AESUtil.decrypt(deliveryAddress);
	}

	public void setDeliveryAddress(String deliveryAddress) throws Exception {
		this.deliveryAddress = AESUtil.encrypt(deliveryAddress);
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
    	
    public Order(String customerName, String deliveryAddress, LocalDateTime orderDate, Float totalPrice, String hmac) {
        this.customerName = customerName;
        this.deliveryAddress = deliveryAddress;
    	this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.hmac = hmac;
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
