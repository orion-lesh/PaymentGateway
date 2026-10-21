package orionlesh.paymentgateway.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record CreateUserRequest(
        @Schema(description = "user email", example = "alex123@gmail.com")
        @NotBlank @Email String email
) {
}
