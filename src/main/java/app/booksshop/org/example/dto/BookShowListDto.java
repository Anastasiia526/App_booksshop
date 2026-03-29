package app.booksshop.org.example.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookShowListDto {

    private Long id;

    private String image;

    @NotEmpty(message = "This field shouldn't be empty")
    private String title;


}
