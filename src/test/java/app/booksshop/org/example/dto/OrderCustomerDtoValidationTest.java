package app.booksshop.org.example.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class OrderCustomerDtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void validDto_hasNoValidationErrors() {
        OrderCustomerDto dto = new OrderCustomerDto();
        dto.setFirstName("Robert");
        dto.setLastName("Martin");
        dto.setEmail("robert.martin@example.com");
        dto.setPhoneNumber("+380501234567");

        Set<ConstraintViolation<OrderCustomerDto>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void invalidDto_hasValidationErrors() {
        OrderCustomerDto dto = new OrderCustomerDto();
        dto.setFirstName("Bob");
        dto.setLastName("Lee");
        dto.setEmail("not-email");
        dto.setPhoneNumber("0501234567");

        Set<ConstraintViolation<OrderCustomerDto>> violations = validator.validate(dto);

        assertThat(violations).hasSize(4);

        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString())
                .containsExactlyInAnyOrder("firstName", "lastName", "email", "phoneNumber");
    }

    @Test
    void nullRequiredFields_haveValidationErrors() {
        OrderCustomerDto dto = new OrderCustomerDto();

        Set<ConstraintViolation<OrderCustomerDto>> violations = validator.validate(dto);

        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString())
                .contains("firstName", "lastName", "email");
    }
}
