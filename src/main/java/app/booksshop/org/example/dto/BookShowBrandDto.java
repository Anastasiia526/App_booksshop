package app.booksshop.org.example.dto;

import app.booksshop.org.example.entities.Author;
import app.booksshop.org.example.entities.BookGenre;
import app.booksshop.org.example.entities.LiteraryGenre;
import jakarta.validation.constraints.NotEmpty;
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
public class BookShowBrandDto {

    private Long id;
    private String image;

    @NotEmpty(message = "This field shouldn't be empty")
    private String title;
    private String brand;
    private Set<AuthorShowDto> authors = new HashSet<>();
    private Set<BookGenreDto> bookGenre = new HashSet<>();
    private LiteraryGenre literaryGenre;


}
