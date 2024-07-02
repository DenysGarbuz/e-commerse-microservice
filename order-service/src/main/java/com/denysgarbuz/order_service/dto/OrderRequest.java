package com.denysgarbuz.order_service.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record OrderRequest(
        @JsonAlias({"items"})
        List<OrderLineItemsDto> orderLineItemsDtos
) {
}
