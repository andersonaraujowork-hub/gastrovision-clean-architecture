package br.com.gastrovision.cleanarchitecture.application.usecase.usertype;

import br.com.gastrovision.cleanarchitecture.application.dto.request.UserTypeRequest;
import br.com.gastrovision.cleanarchitecture.application.dto.response.UserTypeResponse;
import br.com.gastrovision.cleanarchitecture.domain.entity.UserType;
import br.com.gastrovision.cleanarchitecture.domain.exception.ResourceNotFoundException;
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
class UserTypeUseCaseTest {

    @Mock
    private UserTypeRepository repository;

    @InjectMocks
    private UserTypeUseCase useCase;

    @Test
    void shouldCreateUserType() {
        UserTypeRequest request = new UserTypeRequest("Cliente");
        when(repository.save(any())).thenReturn(new UserType(1L, "Cliente"));

        UserTypeResponse response = useCase.create(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Cliente");
    }

    @Test
    void shouldFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(new UserType(1L, "Cliente")));

        UserTypeResponse response = useCase.findById(1L);

        assertThat(response.name()).isEqualTo("Cliente");
    }

    @Test
    void shouldThrowWhenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldFindAll() {
        when(repository.findAll()).thenReturn(List.of(
                new UserType(1L, "Cliente"),
                new UserType(2L, "Dono de Restaurante")
        ));

        List<UserTypeResponse> result = useCase.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldUpdateUserType() {
        when(repository.findById(1L)).thenReturn(Optional.of(new UserType(1L, "Cliente")));
        when(repository.save(any())).thenReturn(new UserType(1L, "Dono de Restaurante"));

        UserTypeResponse response = useCase.update(1L, new UserTypeRequest("Dono de Restaurante"));

        assertThat(response.name()).isEqualTo("Dono de Restaurante");
    }

    @Test
    void shouldDeleteUserType() {
        when(repository.existsById(1L)).thenReturn(true);

        assertThatCode(() -> useCase.delete(1L)).doesNotThrowAnyException();
        verify(repository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeleteNotFound() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> useCase.delete(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
