package br.com.gastrovision.cleanarchitecture.application.usecase.restaurant;

import br.com.gastrovision.cleanarchitecture.application.dto.request.RestaurantRequest;
import br.com.gastrovision.cleanarchitecture.application.dto.response.RestaurantResponse;
import br.com.gastrovision.cleanarchitecture.domain.entity.Restaurant;
import br.com.gastrovision.cleanarchitecture.domain.entity.User;
import br.com.gastrovision.cleanarchitecture.domain.entity.UserType;
import br.com.gastrovision.cleanarchitecture.domain.exception.ResourceNotFoundException;
import br.com.gastrovision.cleanarchitecture.domain.repository.RestaurantRepository;
import br.com.gastrovision.cleanarchitecture.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RestaurantUseCase useCase;

    private final UserType userType = new UserType(1L, "Dono de Restaurante");
    private final User owner = new User(1L, "Maria", "maria@email.com", userType);
    private final Restaurant restaurant = new Restaurant(1L, "Restaurante A", "Rua X", "Italiana", "12h-22h", owner);

    @Test
    void shouldCreateRestaurant() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(restaurantRepository.save(any())).thenReturn(restaurant);

        RestaurantResponse response = useCase.create(
                new RestaurantRequest("Restaurante A", "Rua X", "Italiana", "12h-22h", 1L));

        assertThat(response.name()).isEqualTo("Restaurante A");
        assertThat(response.owner().id()).isEqualTo(1L);
    }

    @Test
    void shouldThrowWhenOwnerNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.create(
                new RestaurantRequest("Restaurante A", "Rua X", "Italiana", "12h-22h", 99L)))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldFindAll() {
        when(restaurantRepository.findAll()).thenReturn(List.of(restaurant));

        assertThat(useCase.findAll()).hasSize(1);
    }

    @Test
    void shouldDeleteRestaurant() {
        when(restaurantRepository.existsById(1L)).thenReturn(true);

        assertThatCode(() -> useCase.delete(1L)).doesNotThrowAnyException();
        verify(restaurantRepository).deleteById(1L);
    }
}
