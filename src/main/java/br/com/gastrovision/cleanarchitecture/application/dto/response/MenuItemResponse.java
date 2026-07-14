package br.com.gastrovision.cleanarchitecture.application.dto.response;

import java.math.BigDecimal;

public record MenuItemResponse(
        Long id, String name, String description,
        BigDecimal price, Boolean onSiteOnly,
        String photoPath, Long restaurantId
) {}