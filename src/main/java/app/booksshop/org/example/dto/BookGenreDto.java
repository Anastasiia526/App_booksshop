package app.booksshop.org.example.dto;

import app.booksshop.org.example.entities.Book;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookGenreDto {

    private Long id;

    @NotEmpty
    @Size(min = 1, max = 50, message = "This field shouldn't be empty")
    private String name;

    private Set<BookShowListDto> books = new HashSet<>();

}
