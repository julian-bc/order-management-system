package com.github.julianbc.inventory_service.repository;

import com.github.julianbc.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByCodeSku(List<String> codeSkuList);
}
