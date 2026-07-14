package br.com.gastrovision.cleanarchitecture.application.dto.response;

public record UserResponse(Long id, String name, String email, UserTypeResponse userType) {}