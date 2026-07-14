package br.com.gastrovision.cleanarchitecture.application.usecase.usertype;

import br.com.gastrovision.cleanarchitecture.application.dto.request.UserTypeRequest;
import br.com.gastrovision.cleanarchitecture.application.dto.response.UserTypeResponse;
import br.com.gastrovision.cleanarchitecture.domain.entity.UserType;
import br.com.gastrovision.cleanarchitecture.domain.exception.ResourceNotFoundException;
import br.com.gastrovision.cleanarchitecture.domain.repository.UserTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeUseCase {

    private final UserTypeRepository repository;

    public UserTypeUseCase(UserTypeRepository repository) {
        this.repository = repository;
    }

    public UserTypeResponse create(UserTypeRequest request) {
        UserType entity = new UserType(null, request.name());
        UserType saved = repository.save(entity);
        return new UserTypeResponse(saved.getId(), saved.getName());
    }

    public UserTypeResponse findById(Long id) {
        UserType entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário não encontrado: " + id));
        return new UserTypeResponse(entity.getId(), entity.getName());
    }

    public List<UserTypeResponse> findAll() {
        return repository.findAll().stream()
                .map(e -> new UserTypeResponse(e.getId(), e.getName()))
                .toList();
    }

    public UserTypeResponse update(Long id, UserTypeRequest request) {
        UserType entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário não encontrado: " + id));
        entity.setName(request.name());
        UserType saved = repository.save(entity);
        return new UserTypeResponse(saved.getId(), saved.getName());
    }

    public void delete(Long id) {
        if (!repository.existsById(id))
            throw new ResourceNotFoundException("Tipo de usuário não encontrado: " + id);
        repository.deleteById(id);
    }
}
