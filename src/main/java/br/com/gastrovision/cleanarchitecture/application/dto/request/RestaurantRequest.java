package br.com.gastrovision.cleanarchitecture.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RestaurantRequest(
        @NotBlank(message = "Nome é obrigatório") String name,
        @NotBlank(message = "Endereço é obrigatório") String address,
        @NotBlank(message = "Tipo de cozinha é obrigatório") String cuisineType,
        @NotBlank(message = "Horário de funcionamento é obrigatório") String openingHours,
        @NotNull(message = "Dono do restaurante é obrigatório") Long ownerId
) {}
