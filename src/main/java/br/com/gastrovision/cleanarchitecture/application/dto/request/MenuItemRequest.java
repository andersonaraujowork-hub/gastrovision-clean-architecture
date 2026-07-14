package br.com.gastrovision.cleanarchitecture.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record MenuItemRequest(
        @NotBlank(message = "Nome é obrigatório") String name,
        @NotBlank(message = "Descrição é obrigatória") String description,
        @NotNull @Positive(message = "Preço deve ser positivo") BigDecimal price,
        @NotNull(message = "Disponibilidade é obrigatória") Boolean onSiteOnly,
        String photoPath
) {}