package com.SecuriDine.OrderService.Service;

import java.util.List;
import java.util.Optional;

import com.SecuriDine.OrderService.DTO.MenuDTO;
import com.SecuriDine.OrderService.Entity.Menu;

import org.springframework.stereotype.Service;

public interface MenuService {
	
    List<MenuDTO> getAllMenuItems();

    
    MenuDTO getMenuItemById(Long menuId);
    
    
    void deleteMenuItem(Long menuId);

	
    MenuDTO saveMenuItem(MenuDTO menuDTO);

	

}

