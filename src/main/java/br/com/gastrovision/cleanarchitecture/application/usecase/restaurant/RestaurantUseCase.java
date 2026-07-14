package br.com.gastrovision.cleanarchitecture.application.usecase.restaurant;

import br.com.gastrovision.cleanarchitecture.application.dto.request.RestaurantRequest;
import br.com.gastrovision.cleanarchitecture.application.dto.response.RestaurantResponse;
import br.com.gastrovision.cleanarchitecture.application.dto.response.UserResponse;
import br.com.gastrovision.cleanarchitecture.application.dto.response.UserTypeResponse;
import br.com.gastrovision.cleanarchitecture.domain.entity.Restaurant;
import br.com.gastrovision.cleanarchitecture.domain.entity.User;
import br.com.gastrovision.cleanarchitecture.domain.exception.ResourceNotFoundException;
import br.com.gastrovision.cleanarchitecture.domain.repository.RestaurantRepository;
import br.com.gastrovision.cleanarchitecture.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantUseCase {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public RestaurantUseCase(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public RestaurantResponse create(RestaurantRequest request) {
        User owner = userRepository.findById(request.ownerId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + request.ownerId()));
        Restaurant saved = restaurantRepository.save(
                new Restaurant(null, request.name(), request.address(), request.cuisineType(), request.openingHours(), owner));
        return toResponse(saved);
    }

    public RestaurantResponse findById(Long id) {
        return toResponse(restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado: " + id)));
    }

    public List<RestaurantResponse> findAll() {
        return restaurantRepository.findAll().stream().map(this::toResponse).toList();
    }

    public RestaurantResponse update(Long id, RestaurantRequest request) {
        Restaurant entity = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado: " + id));
        User owner = userRepository.findById(request.ownerId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + request.ownerId()));
        entity.setName(request.name());
        entity.setAddress(request.address());
        entity.setCuisineType(request.cuisineType());
        entity.setOpeningHours(request.openingHours());
        entity.setOwner(owner);
        return toResponse(restaurantRepository.save(entity));
    }

    public void delete(Long id) {
        if (!restaurantRepository.existsById(id))
            throw new ResourceNotFoundException("Restaurante não encontrado: " + id);
        restaurantRepository.deleteById(id);
    }

    private RestaurantResponse toResponse(Restaurant r) {
        UserTypeResponse ut = new UserTypeResponse(r.getOwner().getUserType().getId(), r.getOwner().getUserType().getName());
        UserResponse owner = new UserResponse(r.getOwner().getId(), r.getOwner().getName(), r.getOwner().getEmail(), ut);
        return new RestaurantResponse(r.getId(), r.getName(), r.getAddress(), r.getCuisineType(), r.getOpeningHours(), owner);
    }
}