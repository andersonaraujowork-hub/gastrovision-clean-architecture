package br.com.gastrovision.cleanarchitecture.application.usecase.menuitem;

import br.com.gastrovision.cleanarchitecture.application.dto.request.MenuItemRequest;
import br.com.gastrovision.cleanarchitecture.application.dto.response.MenuItemResponse;
import br.com.gastrovision.cleanarchitecture.domain.entity.MenuItem;
import br.com.gastrovision.cleanarchitecture.domain.entity.Restaurant;
import br.com.gastrovision.cleanarchitecture.domain.exception.ResourceNotFoundException;
import br.com.gastrovision.cleanarchitecture.domain.repository.MenuItemRepository;
import br.com.gastrovision.cleanarchitecture.domain.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemUseCase {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuItemUseCase(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public MenuItemResponse create(Long restaurantId, MenuItemRequest request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado: " + restaurantId));
        MenuItem saved = menuItemRepository.save(
                new MenuItem(null, request.name(), request.description(), request.price(), request.onSiteOnly(), request.photoPath(), restaurant));
        return toResponse(saved);
    }

    public MenuItemResponse findById(Long restaurantId, Long id) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado: " + id));
        return toResponse(item);
    }

    public List<MenuItemResponse> findByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId).stream().map(this::toResponse).toList();
    }

    public MenuItemResponse update(Long restaurantId, Long id, MenuItemRequest request) {
        MenuItem entity = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado: " + id));
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setPrice(request.price());
        entity.setOnSiteOnly(request.onSiteOnly());
        entity.setPhotoPath(request.photoPath());
        return toResponse(menuItemRepository.save(entity));
    }

    public void delete(Long restaurantId, Long id) {
        if (!menuItemRepository.existsById(id))
            throw new ResourceNotFoundException("Item não encontrado: " + id);
        menuItemRepository.deleteById(id);
    }

    private MenuItemResponse toResponse(MenuItem m) {
        return new MenuItemResponse(m.getId(), m.getName(), m.getDescription(),
                m.getPrice(), m.getOnSiteOnly(), m.getPhotoPath(), m.getRestaurant().getId());
    }
}