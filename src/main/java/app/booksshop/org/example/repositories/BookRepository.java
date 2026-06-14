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

    @Query("""
            SELECT DISTINCT b
            FROM Book b
            LEFT JOIN b.authors a
            LEFT JOIN b.bookGenre bg
            LEFT JOIN b.literaryGenre lg
            WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(b.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(b.language) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(b.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(a.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(bg.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(lg.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR CAST(b.productCode AS string) LIKE CONCAT('%', :keyword, '%')
               OR CAST(b.yearOfPublication AS string) LIKE CONCAT('%', :keyword, '%')
            """)
    List<Book> searchBooks(@Param("keyword") String keyword);

    List<Book> findByAvailableTrue();

    void deleteById(Long id);
}
