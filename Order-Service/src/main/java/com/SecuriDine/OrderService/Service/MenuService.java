package com.SecuriDine.OrderService.Service;

import java.util.List;
import java.util.Optional;

import com.SecuriDine.OrderService.DTO.MenuDTO;
import com.SecuriDine.OrderService.Entity.Menu;

import org.springframework.stereotype.Service;

public interface MenuService {
	
    List<MenuDTO> getAllMenuItems() throws Exception;

    
    MenuDTO getMenuItemById(Long menuId) throws Exception;
    
    
    void deleteMenuItem(Long menuId) throws Exception;

	
    MenuDTO saveMenuItem(MenuDTO menuDTO) throws Exception;

	

}

