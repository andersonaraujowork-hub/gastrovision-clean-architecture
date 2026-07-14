package br.com.gastrovision.cleanarchitecture.infrastructure.web.controller;

import br.com.gastrovision.cleanarchitecture.application.dto.request.MenuItemRequest;
import br.com.gastrovision.cleanarchitecture.application.dto.response.MenuItemResponse;
import br.com.gastrovision.cleanarchitecture.application.usecase.menuitem.MenuItemUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/menu-items")
public class MenuItemController {

    private final MenuItemUseCase useCase;

    public MenuItemController(MenuItemUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<MenuItemResponse> create(@PathVariable Long restaurantId, @Valid @RequestBody MenuItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.create(restaurantId, request));
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponse>> findAll(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(useCase.findByRestaurant(restaurantId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponse> findById(@PathVariable Long restaurantId, @PathVariable Long id) {
        return ResponseEntity.ok(useCase.findById(restaurantId, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponse> update(@PathVariable Long restaurantId, @PathVariable Long id, @Valid @RequestBody MenuItemRequest request) {
        return ResponseEntity.ok(useCase.update(restaurantId, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long restaurantId, @PathVariable Long id) {
        useCase.delete(restaurantId, id);
        return ResponseEntity.noContent().build();
    }
}
