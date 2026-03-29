package app.booksshop.org.example.repositories;

import app.booksshop.org.example.entities.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookGenreRepository extends JpaRepository<BookGenre, Long> {

    List<BookGenre> findBookGenresByLiteraryGenreId(Long id);
}
