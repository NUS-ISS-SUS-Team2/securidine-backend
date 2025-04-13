package com.SecuriDine.OrderService.Service;

import com.SecuriDine.OrderService.Entity.Menu;
import com.SecuriDine.OrderService.DTO.MenuDTO;
import com.SecuriDine.OrderService.Repository.MenuRepository;
import com.SecuriDine.OrderService.Util.AESUtil;
import com.SecuriDine.OrderService.Util.HMACUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.SecuriDine.OrderService.Entity.Restaurant;
import com.SecuriDine.OrderService.DTO.RestaurantDTO;
import com.SecuriDine.OrderService.Service.RestaurantService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;



@Component
@Service
public class MenuServiceImpl implements MenuService{

    private  MenuRepository menuRepository;
    private RestaurantService restaurantService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    // Save a menu item (create or update)
    @Override
    public MenuDTO saveMenuItem(MenuDTO menuDTO) throws Exception {
        Menu menuItem;
        if (menuDTO.getMenuId() != null) {
            // Update existing menu item
            Optional<Menu> optionalMenuItem = menuRepository.findById(menuDTO.getMenuId());
            if (optionalMenuItem.isPresent()) {
                menuItem = optionalMenuItem.get();
                menuItem.setItemName(menuDTO.getItemName());
                menuItem.setDescription(menuDTO.getDescription());
                menuItem.setPrice(menuDTO.getPrice());
            } else {
                throw new RuntimeException("Menu item not found");
            }
        } else {
            // Create new menu item
            menuItem = new Menu(menuDTO.getItemName(), menuDTO.getDescription(), menuDTO.getPrice(), null);
        }
        // Set restaurant if provided
        if (menuDTO.getRestaurantId() != null) {
            
            try {
            RestaurantDTO restaurantDTO = restaurantService.getRestaurantById(menuDTO.getRestaurantId());
            Restaurant restaurant = new Restaurant();
            restaurant.setRestaurantId(restaurantDTO.getRestaurantId());
            restaurant.setName(restaurantDTO.getName());
            restaurant.setLocation(restaurantDTO.getLocation());
            menuItem.setRestaurant(restaurant);
        } catch (Exception e) {
        throw new RuntimeException("Restaurant not found");
        }
        }
        menuItem.computeHMAC();
        Menu savedMenuItem = menuRepository.save(menuItem);
        return savedMenuItem.convertToDTO();
    }

    // Read all menu items
    // @Override
    // public List<MenuDTO> getAllMenuItems() throws Exception {
    //     List<Menu> menuItems = menuRepository.findAll();
    //     return menuItems.stream()
    //             .map(menuItem -> {
    //                 try {
    //                     return menuItem.convertToDTO();
    //                 } catch (Exception e) {
    //                     throw new RuntimeException(e);
    //                 }
    //             })
    //             .toList();
    // }



     @Override
    public List<MenuDTO> getAllMenuItems() {
    	LOGGER.info("LOG FOR Menu - In GET ALL Menu Items");
    	Stream<Menu> Menustream = StreamSupport.stream(menuRepository
    								.findAll().spliterator(), false)
					                .filter(menu -> {
					                    try {
					                        boolean isValid = menu.verifyHMAC();
					                        if (!isValid) {
					                            LOGGER.warn("HMAC verification failed for menu item ID: " + menu.getMenuId());
					                        }
					                        return isValid;
					                    } catch (Exception e) {
					                        LOGGER.error("Error verifying HMAC for menu item ID: " + menu.getMenuId(), e);
					                        return false;  // Exclude corrupted orders
					                    }
					                });
        
    	
    	return Menustream
                .map(menu -> {
            try {
                return menu.convertToDTO();
            } catch (Exception e) {
                LOGGER.error("Error converting restaurant to DTO for ID: " + menu.getMenuId(), e);
                return null;
            }
        })
                .collect(Collectors.toList());
    }

    // Read a menu item by ID
    @Override
    public MenuDTO getMenuItemById(Long menuId) throws Exception {
        Optional<Menu> optionalMenuItem = menuRepository.findById(menuId);
        if (optionalMenuItem.isPresent()) {
            Menu menuItem = optionalMenuItem.get();
            return menuItem.convertToDTO();
        } else {
            throw new RuntimeException("Menu item not found");
        }
    }

    // Delete a menu item
    @Override
    public void deleteMenuItem(Long menuId) {
        menuRepository.deleteById(menuId);
    }

    // Verify HMAC for a menu item
    public boolean verifyMenuItemHMAC(Long menuId) throws Exception {
        Optional<Menu> optionalMenuItem = menuRepository.findById(menuId);
        if (optionalMenuItem.isPresent()) {
            Menu menuItem = optionalMenuItem.get();
            return menuItem.verifyHMAC();
        } else {
            throw new RuntimeException("Menu item not found");
        }
    }
}

