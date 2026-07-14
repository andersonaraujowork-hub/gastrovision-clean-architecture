package br.com.gastrovision.cleanarchitecture.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserTypeRequest(
        @NotBlank(message = "Nome é obrigatório") String name
) {}
