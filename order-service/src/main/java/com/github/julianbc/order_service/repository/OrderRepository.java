package com.github.julianbc.order_service.repository;

import com.github.julianbc.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
