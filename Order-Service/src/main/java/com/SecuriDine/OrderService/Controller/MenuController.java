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

import com.SecuriDine.OrderService.DTO.MenuDTO;
import com.SecuriDine.OrderService.DTO.MenuDTOTest;
import com.SecuriDine.OrderService.Repository.MenuRepository;
import com.SecuriDine.OrderService.Service.MenuService;
import com.SecuriDine.OrderService.Service.MenuServiceImpl;

@CrossOrigin
@RestController
public class MenuController {

    private MenuService menuService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
	

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/menu/getAllMenuItems")
	@ResponseBody
    public List<MenuDTO> getAllMenuItemsApi() throws Exception{
        System.out.println("getAll Menu items endpoint works!");
		LOGGER.info("LOG - CALLING Menu CONTROLLER");
	    List <MenuDTO> getAllMenuItems = menuService.getAllMenuItems();
	    System.out.print("AllMenuItems" + getAllMenuItems);
	    return getAllMenuItems;
    }
	
	
    @GetMapping("/menu/testEndpoint")
    @ResponseBody
    public MenuDTOTest yourEndpoint() {
    	HttpHeaders headers = new HttpHeaders();
	    headers.set("Content-Type", "application/text");
    	System.out.println("Test Endpoint works!");
    	MenuDTOTest test = new MenuDTOTest("This data is from Menu Service API!");
        return test;
    }

    
}
