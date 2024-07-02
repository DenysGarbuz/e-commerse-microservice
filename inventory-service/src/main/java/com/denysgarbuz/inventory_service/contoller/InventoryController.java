package com.denysgarbuz.inventory_service.contoller;


import com.denysgarbuz.inventory_service.dto.InventoryResponse;
import com.denysgarbuz.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        return this.inventoryService.isInStock(skuCode);
    }
}
