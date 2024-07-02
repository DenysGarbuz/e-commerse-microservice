package com.denysgarbuz.order_service.dto;


public record InventoryResponse(
        String skuCode,
        boolean isInStock
){
}
