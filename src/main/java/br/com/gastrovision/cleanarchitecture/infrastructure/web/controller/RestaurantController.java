package br.com.gastrovision.cleanarchitecture.infrastructure.web.controller;

import br.com.gastrovision.cleanarchitecture.application.dto.request.RestaurantRequest;
import br.com.gastrovision.cleanarchitecture.application.dto.response.RestaurantResponse;
import br.com.gastrovision.cleanarchitecture.application.usecase.restaurant.RestaurantUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantUseCase useCase;

    public RestaurantController(RestaurantUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> create(@Valid @RequestBody RestaurantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.create(request));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> findAll() {
        return ResponseEntity.ok(useCase.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(useCase.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> update(@PathVariable Long id, @Valid @RequestBody RestaurantRequest request) {
        return ResponseEntity.ok(useCase.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
