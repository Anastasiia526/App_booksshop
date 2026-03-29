package app.booksshop.org.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "literary_genres")
public class LiteraryGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "This field shouldn't be empty")
    private String name;

    @OneToMany(mappedBy = "literaryGenre")
    private Set<BookGenre> bookGenre = new HashSet<>();

    @OneToMany(mappedBy = "literaryGenre")
    private Set<Book>  books = new HashSet<>();


}
