package br.com.gastrovision.cleanarchitecture.application.usecase.user;

import br.com.gastrovision.cleanarchitecture.application.dto.request.UserRequest;
import br.com.gastrovision.cleanarchitecture.application.dto.response.UserResponse;
import br.com.gastrovision.cleanarchitecture.application.dto.response.UserTypeResponse;
import br.com.gastrovision.cleanarchitecture.domain.entity.User;
import br.com.gastrovision.cleanarchitecture.domain.entity.UserType;
import br.com.gastrovision.cleanarchitecture.domain.exception.ResourceNotFoundException;
import br.com.gastrovision.cleanarchitecture.domain.repository.UserRepository;
import br.com.gastrovision.cleanarchitecture.domain.repository.UserTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserUseCase {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;

    public UserUseCase(UserRepository userRepository, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
    }

    public UserResponse create(UserRequest request) {
        UserType userType = userTypeRepository.findById(request.userTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário não encontrado: " + request.userTypeId()));
        User saved = userRepository.save(new User(null, request.name(), request.email(), userType));
        return toResponse(saved);
    }

    public UserResponse findById(Long id) {
        return toResponse(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + id)));
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    public UserResponse update(Long id, UserRequest request) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + id));
        UserType userType = userTypeRepository.findById(request.userTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário não encontrado: " + request.userTypeId()));
        entity.setName(request.name());
        entity.setEmail(request.email());
        entity.setUserType(userType);
        return toResponse(userRepository.save(entity));
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id))
            throw new ResourceNotFoundException("Usuário não encontrado: " + id);
        userRepository.deleteById(id);
    }

    private UserResponse toResponse(User u) {
        UserTypeResponse ut = new UserTypeResponse(u.getUserType().getId(), u.getUserType().getName());
        return new UserResponse(u.getId(), u.getName(), u.getEmail(), ut);
    }
}
