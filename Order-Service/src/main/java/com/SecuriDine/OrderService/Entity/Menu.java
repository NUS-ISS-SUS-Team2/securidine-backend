package com.SecuriDine.OrderService.Entity;

import jakarta.persistence.*;
import com.SecuriDine.OrderService.DTO.MenuDTO;
import com.SecuriDine.OrderService.Util.AESUtil;
import com.SecuriDine.OrderService.Util.HMACUtil;

@Entity
@Table(name = "menu_items")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Float price;

    @Column(name = "hmac_string", nullable = false)
    private String hmac;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public void computeHMAC() throws Exception {
        String data = itemName + description + price;
        this.hmac = HMACUtil.generateHMAC(data);
    }

    public boolean verifyHMAC() throws Exception {
        String data = itemName + description + price;
        return HMACUtil.verifyHMAC(data, this.hmac);
    }

    public String getItemName() throws Exception {
        return AESUtil.decrypt(itemName);
    }

    public void setItemName(String itemName) throws Exception {
        this.itemName = AESUtil.encrypt(itemName);
    }

    public String getDescription() throws Exception {
        return AESUtil.decrypt(description);
    }

    public void setDescription(String description) throws Exception {
        this.description = AESUtil.encrypt(description);
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Menu() {}

    public Menu(String itemName, String description, Float price, Restaurant restaurant) throws Exception {
        this.itemName = AESUtil.encrypt(itemName);
        this.description = AESUtil.encrypt(description);
        this.price = price;
        this.restaurant = restaurant;
    }

    public MenuDTO convertToDTO() throws Exception {
        MenuDTO dto = new MenuDTO();
        dto.setMenuId(this.menuId);
        dto.setItemName(getItemName());
        dto.setDescription(getDescription());
        dto.setPrice(this.price);
        dto.setRestaurantId(this.restaurant.getRestaurantId());
        return dto;
    }
}
