package com.github.julianbc.inventory_service.service;

import com.github.julianbc.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository repository;

    @Transactional(readOnly = true)
    public boolean isInStock(String codeSku) {
        return repository.findByCodeSku(codeSku).isPresent();
    }
}
