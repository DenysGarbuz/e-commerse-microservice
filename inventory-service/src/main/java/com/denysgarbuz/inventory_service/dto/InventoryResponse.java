package com.denysgarbuz.inventory_service.dto;


public record InventoryResponse(
        String skuCode,
        boolean isInStock
){
}
