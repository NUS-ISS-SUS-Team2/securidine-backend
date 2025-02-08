package com.SecuriDine.OrderService.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.SecuriDine.OrderService.Entity.Order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query("SELECT o FROM Order o WHERE o.customerName = :customerName")
	Optional<Order> findByCustName(@Param("customerName") String customerName);
	
	@Query("SELECT o FROM Order o")
    List<Order> getAllOrders();



}
