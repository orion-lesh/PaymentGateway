package orionlesh.paymentgateway.model.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import orionlesh.paymentgateway.model.UserEntity;

@Component
public class UserEntityMapper {
    PaymentEntityMapper paymentEntityMapper;

    public UserEntityMapper(PaymentEntityMapper paymentEntityMapper) {
        this.paymentEntityMapper = paymentEntityMapper;
    }
    public UserDto convertEntityToDto(UserEntity userEntity) {
        return new UserDto(
                userEntity.getId(),
                userEntity.getEmail(),
                paymentEntityMapper.mapListToDto(userEntity.getPayments())
        );
    }
}
