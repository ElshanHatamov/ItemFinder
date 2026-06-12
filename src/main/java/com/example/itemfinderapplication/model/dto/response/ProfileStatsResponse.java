package com.example.itemfinderapplication.model.dto.response;

public record ProfileStatsResponse(
        long totalItems,
        long lostItems,
        long foundItems
) {
}
