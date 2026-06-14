package app.booksshop.org.example.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BookShowDetailsDtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void validDto_hasNoValidationErrors() {
        BookShowDetailsDto dto = new BookShowDetailsDto();
        dto.setProductCode(123456L);
        dto.setIsbn("9780132350884");
        dto.setTitle("Clean Code");
        dto.setYearOfPublication(2008);
        dto.setPrice(45.99);
        dto.setAvailable(true);

        Set<ConstraintViolation<BookShowDetailsDto>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void invalidDto_hasValidationErrors() {
        BookShowDetailsDto dto = new BookShowDetailsDto();
        dto.setIsbn("");
        dto.setTitle("");
        dto.setYearOfPublication(1600);
        dto.setPrice(0);
        dto.setAvailable(null);

        Set<ConstraintViolation<BookShowDetailsDto>> violations = validator.validate(dto);

        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString())
                .contains("isbn", "title", "yearOfPublication", "price", "available");
    }
}
