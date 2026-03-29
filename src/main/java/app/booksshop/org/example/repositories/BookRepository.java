package app.booksshop.org.example.repositories;

import app.booksshop.org.example.dto.BookShowDetailsDto;
import app.booksshop.org.example.dto.BookShowListDto;
import app.booksshop.org.example.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByBookGenreId(Long id);

    @Query("""
       SELECT b
       FROM Book b
       WHERE b.literaryGenre.id = :genreId
       """)
    List<Book> findBooksByLiteraryGenreId(@Param("genreId") Long genreId);

    Long id(Long id);

    List<Book> findDistinctByAuthors_Id(Long id);

    List<Book> findByBrand(String brand);

    Optional<Book> findByIdAndAvailableTrue(Long id);

    List<Book> findByAvailableFalse();

    List<Book> findByAvailableTrue();

    void deleteById(Long id);
}
