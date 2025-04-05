package com.SecuriDine.OrderService.DTO;

import jakarta.persistence.Entity;

public class RestaurantDTOTest {
	private String message;

	public RestaurantDTOTest(String message) {
		this.setMessage(message);
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
