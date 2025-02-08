package com.SecuriDine.OrderService.DTO;

import jakarta.persistence.Entity;

public class OrderDTOTest {
	private String message;

	public OrderDTOTest(String message) {
		this.setMessage(message);
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
