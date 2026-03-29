package app.booksshop.org.example.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPayDto {

    private Long id;

    @NotBlank(message = "Card number is required")
    @Pattern(regexp = "\\d{16}", message = "Card must be 16 digits")
    private String cardNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
            message = "Must be formatted MM/YY")
    private String expirationDate;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String cvv;


}
