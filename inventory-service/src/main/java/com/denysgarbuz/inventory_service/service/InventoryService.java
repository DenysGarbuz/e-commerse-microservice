package com.denysgarbuz.inventory_service.service;


import com.denysgarbuz.inventory_service.dto.InventoryResponse;
import com.denysgarbuz.inventory_service.model.Inventory;
import com.denysgarbuz.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return this.inventoryRepository.findBySkuCodeIn(skuCode)
                .stream()
                .map(this::toInventoryResponse)
                .toList();


    }

    private InventoryResponse toInventoryResponse(Inventory inventory) {
        return new InventoryResponse(
                inventory.getSkuCode(),
                inventory.getQuantity() > 0

        );
    }

}

