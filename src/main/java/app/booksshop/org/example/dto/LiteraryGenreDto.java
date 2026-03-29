package app.booksshop.org.example.dto;

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
public class LiteraryGenreDto {
    private Long id;
    private String name;
    private Set<BookShowDetailsDto> books = new HashSet<>();

}
