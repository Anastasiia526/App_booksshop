package app.booksshop.org.example.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCustomerDto {

    private Long id;

    @Size(min = 5, message = "Last name must be at least 5 characters long")
    @NotNull
    private String lastName;

    @Size(min = 5, message = "First name must be at least 5 characters long")
    @NotNull
    private String firstName;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email is not valid")
    private String email;

    @Pattern(
            regexp = "^\\+380\\d{9}$",
            message = "The number must be in the format +380XXXXXXXXX"
    )
    private String phoneNumber;


}
