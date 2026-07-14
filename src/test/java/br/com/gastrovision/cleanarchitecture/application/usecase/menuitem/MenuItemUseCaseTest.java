package br.com.gastrovision.cleanarchitecture.application.usecase.menuitem;

import br.com.gastrovision.cleanarchitecture.application.dto.request.MenuItemRequest;
import br.com.gastrovision.cleanarchitecture.application.dto.response.MenuItemResponse;
import br.com.gastrovision.cleanarchitecture.domain.entity.MenuItem;
import br.com.gastrovision.cleanarchitecture.domain.entity.Restaurant;
import br.com.gastrovision.cleanarchitecture.domain.entity.User;
import br.com.gastrovision.cleanarchitecture.domain.entity.UserType;
import br.com.gastrovision.cleanarchitecture.domain.exception.ResourceNotFoundException;
import br.com.gastrovision.cleanarchitecture.domain.repository.MenuItemRepository;
import br.com.gastrovision.cleanarchitecture.domain.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemUseCaseTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private MenuItemUseCase useCase;

    private final UserType userType = new UserType(1L, "Dono de Restaurante");
    private final User owner = new User(1L, "Maria", "maria@email.com", userType);
    private final Restaurant restaurant = new Restaurant(1L, "Restaurante A", "Rua X", "Italiana", "12h-22h", owner);
    private final MenuItem menuItem = new MenuItem(1L, "Pizza", "Pizza margherita", BigDecimal.valueOf(45.90), true, "/photos/pizza.jpg", restaurant);

    @Test
    void shouldCreateMenuItem() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(menuItemRepository.save(any())).thenReturn(menuItem);

        MenuItemResponse response = useCase.create(1L,
                new MenuItemRequest("Pizza", "Pizza margherita", BigDecimal.valueOf(45.90), true, "/photos/pizza.jpg"));

        assertThat(response.name()).isEqualTo("Pizza");
        assertThat(response.restaurantId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowWhenRestaurantNotFound() {
        when(restaurantRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.create(99L,
                new MenuItemRequest("Pizza", "Pizza margherita", BigDecimal.valueOf(45.90), true, null)))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldFindByRestaurant() {
        when(menuItemRepository.findByRestaurantId(1L)).thenReturn(List.of(menuItem));

        assertThat(useCase.findByRestaurant(1L)).hasSize(1);
    }

    @Test
    void shouldDeleteMenuItem() {
        when(menuItemRepository.existsById(1L)).thenReturn(true);

        assertThatCode(() -> useCase.delete(1L, 1L)).doesNotThrowAnyException();
        verify(menuItemRepository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeleteNotFound() {
        when(menuItemRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> useCase.delete(1L, 99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
