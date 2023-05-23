package uz.jk.onlineshopbyspringboot.domain.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateDto {
    String name;
    @Email(message = "valid email required", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @NotBlank(message = "email cannot be null or empty")
    String email;
    @NotNull
    @NotBlank(message = "password cannot be null or empty")
    String password;
}
