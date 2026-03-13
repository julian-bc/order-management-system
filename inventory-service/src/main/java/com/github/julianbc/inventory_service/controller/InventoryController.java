package com.github.julianbc.inventory_service.controller;

import com.github.julianbc.inventory_service.dtos.InventoryResponse;
import com.github.julianbc.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService service;

    @GetMapping
    public List<InventoryResponse> isInStock(@RequestParam List<String> codeSkuList) {
        return service.isInStock(codeSkuList);
    }
}
