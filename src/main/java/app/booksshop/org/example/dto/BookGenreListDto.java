package app.booksshop.org.example.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookGenreListDto {

    private Long id;

    @NotEmpty
    @Size(min = 1, max = 50, message = "This field shouldn't be empty")
    private String name;


//    private Set<BookGenre> bookGenre = new HashSet<>();
}
