package orionlesh.paymentgateway.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import orionlesh.paymentgateway.model.UserEntity;
import orionlesh.paymentgateway.model.dto.CreateUserRequest;
import orionlesh.paymentgateway.model.dto.UserDto;
import orionlesh.paymentgateway.model.dto.UserEntityMapper;
import orionlesh.paymentgateway.repository.PaymentRepository;
import orionlesh.paymentgateway.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    public UserService(UserRepository userRepository, UserEntityMapper userEntityMapper, PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
    }
    @Transactional
    public UserDto create(CreateUserRequest cut) {
        boolean exists = userRepository.existsByEmailIgnoreCase(cut.email());
        if (exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email: " + cut.email() + "already exists");
        }
        UserEntity userEntity = new UserEntity(cut.email());
        userRepository.save(userEntity);
        return userEntityMapper.convertEntityToDto(userEntity);
    }
    @Transactional
    public UserDto getUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: " + id + "not found"));
        return  userEntityMapper.convertEntityToDto(userEntity);
    }
    @Transactional
    public List<UserDto> getAllUsers() {
        return userRepository.findAllWithPayments().stream()
                .map(user -> userEntityMapper.convertEntityToDto(user))
                .toList();
    }
}
