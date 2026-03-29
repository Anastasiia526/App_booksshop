package app.booksshop.org.example.dto;

import app.booksshop.org.example.entities.Book;
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
public class AuthorShowDto {

    private Long id;
    private String fullName;
    private Set<BookShowListDto> books = new HashSet<>();

}
