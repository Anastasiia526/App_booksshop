package app.booksshop.org.example.services.interfaces;

import app.booksshop.org.example.dto.BookShowBrandDto;
import app.booksshop.org.example.dto.BookShowDetailsDto;
import app.booksshop.org.example.dto.BookShowListDto;
import app.booksshop.org.example.entities.Book;

import java.util.List;

public interface BookServiceInterface {

    List<BookShowListDto> findByBookGenreId(Long id);

    BookShowDetailsDto findByBookId(Long id);

    Book findById(Long id);

    List<BookShowListDto> findByLiteraryGenreId(Long Id);

    List<BookShowListDto> findByAuthorId(Long id);

    List<BookShowBrandDto> findByBrand(String brand);

    BookShowDetailsDto findByIdAndAvailableTrue(Long id);

    List<BookShowListDto> findAvailableFalse();

    List<BookShowListDto> findAvailableTrue();

    void update(long id, BookShowDetailsDto updatedBook);

    void delete(Long id);

    void createBook(BookShowDetailsDto book);

}
