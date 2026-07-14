package br.com.gastrovision.cleanarchitecture.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotBlank(message = "Nome é obrigatório") String name,
        @NotBlank @Email(message = "Email inválido") String email,
        @NotNull(message = "Tipo de usuário é obrigatório") Long userTypeId
) {}