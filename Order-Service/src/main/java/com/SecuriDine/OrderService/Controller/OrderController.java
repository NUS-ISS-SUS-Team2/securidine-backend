package com.SecuriDine.OrderService.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.SecuriDine.OrderService.DTO.OrderDTO;
import com.SecuriDine.OrderService.DTO.OrderDTOTest;
import com.SecuriDine.OrderService.Repository.OrderRepository;
import com.SecuriDine.OrderService.Service.OrderService;
import com.SecuriDine.OrderService.Service.OrderServiceImpl;

@CrossOrigin(origins = {"https://nusiss-sus-project.online","https://ordersapi.nusiss-sus-project.online", "https://0vhwby6emi.execute-api.ap-southeast-1.amazonaws.com/prod"})
@RestController
public class OrderController {

	private OrderService orderService;
	
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
	
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
	
	@GetMapping("/order/getAllOrders")
	@ResponseBody
	public List <OrderDTO> getAllOrdersApi(){
		System.out.println("getAll Order endpoint works!");
		LOGGER.info("LOG FOR BZ - CALLING CONTROLLER");
	    List <OrderDTO> allOrders = orderService.getAllOrders();
	    System.out.print("allOrders" + allOrders);
	    return allOrders;
	}
	
    @GetMapping("/order/testEndpoint")
    @ResponseBody
    public OrderDTOTest yourEndpoint() {
    	HttpHeaders headers = new HttpHeaders();
	    headers.set("Content-Type", "application/text");
    	System.out.println("Test Endpoint works!");
    	OrderDTOTest test = new OrderDTOTest("This data is from Order Service API!");
        return test;
    }
	
}
