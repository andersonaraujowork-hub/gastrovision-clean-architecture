package br.com.gastrovision.cleanarchitecture.application.dto.response;

public record RestaurantResponse(
        Long id, String name, String address,
        String cuisineType, String openingHours,
        UserResponse owner
) {}