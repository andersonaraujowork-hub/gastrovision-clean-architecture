package br.com.gastrovision.cleanarchitecture.application.usecase.user;

import br.com.gastrovision.cleanarchitecture.application.dto.request.UserRequest;
import br.com.gastrovision.cleanarchitecture.application.dto.response.UserResponse;
import br.com.gastrovision.cleanarchitecture.domain.entity.User;
import br.com.gastrovision.cleanarchitecture.domain.entity.UserType;
import br.com.gastrovision.cleanarchitecture.domain.exception.ResourceNotFoundException;
import br.com.gastrovision.cleanarchitecture.domain.repository.UserRepository;
import br.com.gastrovision.cleanarchitecture.domain.repository.UserTypeRepository;
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
class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserUseCase useCase;

    private final UserType userType = new UserType(1L, "Cliente");
    private final User user = new User(1L, "João", "joao@email.com", userType);

    @Test
    void shouldCreateUser() {
        when(userTypeRepository.findById(1L)).thenReturn(Optional.of(userType));
        when(userRepository.save(any())).thenReturn(user);

        UserResponse response = useCase.create(new UserRequest("João", "joao@email.com", 1L));

        assertThat(response.name()).isEqualTo("João");
        assertThat(response.email()).isEqualTo("joao@email.com");
    }

    @Test
    void shouldThrowWhenUserTypeNotFoundOnCreate() {
        when(userTypeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.create(new UserRequest("João", "joao@email.com", 99L)))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldFindById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse response = useCase.findById(1L);

        assertThat(response.id()).isEqualTo(1L);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldFindAll() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        assertThat(useCase.findAll()).hasSize(1);
    }

    @Test
    void shouldDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        assertThatCode(() -> useCase.delete(1L)).doesNotThrowAnyException();
        verify(userRepository).deleteById(1L);
    }
}
