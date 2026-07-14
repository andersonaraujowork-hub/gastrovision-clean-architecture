package br.com.gastrovision.cleanarchitecture.infrastructure.web.controller;

import br.com.gastrovision.cleanarchitecture.application.dto.request.UserTypeRequest;
import br.com.gastrovision.cleanarchitecture.application.dto.response.UserTypeResponse;
import br.com.gastrovision.cleanarchitecture.application.usecase.usertype.UserTypeUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-types")
public class UserTypeController {

    private final UserTypeUseCase useCase;

    public UserTypeController(UserTypeUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<UserTypeResponse> create(@Valid @RequestBody UserTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.create(request));
    }

    @GetMapping
    public ResponseEntity<List<UserTypeResponse>> findAll() {
        return ResponseEntity.ok(useCase.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTypeResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(useCase.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserTypeResponse> update(@PathVariable Long id, @Valid @RequestBody UserTypeRequest request) {
        return ResponseEntity.ok(useCase.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
