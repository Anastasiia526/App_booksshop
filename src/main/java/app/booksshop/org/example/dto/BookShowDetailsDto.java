package app.booksshop.org.example.dto;

import app.booksshop.org.example.entities.LiteraryGenre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookShowDetailsDto {

    private Long id;
    private String image;

    @NotNull
    private long productCode;

    @NotBlank
    @Size(min = 1, max = 20)
    private String isbn;

    @NotEmpty(message = "This field shouldn't be empty")
    @Size(min = 1, max = 200)
    private String title;
    private String brand;
    private int yearOfPublication;
    private String language;
    private double price;

    @NotNull
    private Boolean available;
    private String description;
    private Set<AuthorShowDto> authors = new HashSet<>();
    private Set<BookGenreListDto> bookGenre = new HashSet<>();
    private LiteraryGenre literaryGenre;


}
