package com.github.julianbc.inventory_service.service;

import com.github.julianbc.inventory_service.dtos.InventoryResponse;
import com.github.julianbc.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository repository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> codeSkuList) {
        return repository.findByCodeSkuIn(codeSkuList).stream()
                .map(inventory -> InventoryResponse.builder()
                        .codeSku(inventory.getCodeSku())
                        .inStock(inventory.getQuantity() > 0)
                        .build()
                ).collect(Collectors.toList());
    }
}
