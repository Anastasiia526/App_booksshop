package app.booksshop.org.example.dto;

import app.booksshop.org.example.entities.enums.DeliveryMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDeliveryDto {

    private Long id;

    @NotBlank(message = "City  is required")
    private String city;

    @NotBlank(message = "Region  is required")
    private String region;

    @NotNull(message = "Select delivery method")
    private DeliveryMethod method;

    @NotBlank(message = "Address is required")
    private String address;
}
