package com.SecuriDine.OrderService.DTO;

import jakarta.persistence.Entity;

public class MenuDTOTest {
	private String message;

	public MenuDTOTest(String message) {
		this.setMessage(message);
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
