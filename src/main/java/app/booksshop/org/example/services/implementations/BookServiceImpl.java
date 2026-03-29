package app.booksshop.org.example.services.implementations;

import app.booksshop.org.example.dto.BookShowBrandDto;
import app.booksshop.org.example.dto.BookShowDetailsDto;
import app.booksshop.org.example.dto.BookShowListDto;
import app.booksshop.org.example.entities.Author;
import app.booksshop.org.example.entities.Book;
import app.booksshop.org.example.entities.BookGenre;
import app.booksshop.org.example.repositories.AuthorRepository;
import app.booksshop.org.example.repositories.BookGenreRepository;
import app.booksshop.org.example.repositories.BookRepository;
import app.booksshop.org.example.services.interfaces.BookServiceInterface;
import app.booksshop.org.example.services.mappers.BookMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookServiceInterface {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookGenreRepository bookGenreRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper,
                           AuthorRepository authorRepository, BookGenreRepository bookGenreRepository) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.authorRepository = authorRepository;
        this.bookGenreRepository = bookGenreRepository;
    }


    @Override
    public List<BookShowListDto> findByBookGenreId(Long id) {
        return bookMapper.toListDtos(bookRepository.findByBookGenreId(id));
    }

    @Override
    public BookShowDetailsDto findByBookId(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow());
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    public List<BookShowListDto> findByLiteraryGenreId(Long Id) {
        return bookMapper.toListDtos(bookRepository.findBooksByLiteraryGenreId(Id));
    }

    @Override
    public List<BookShowListDto> findByAuthorId(Long id) {
        return bookMapper.toListDtos(bookRepository.findDistinctByAuthors_Id(id));
    }

    @Override
    public List<BookShowBrandDto> findByBrand(String brand) {
        return bookMapper.toDtos(bookRepository.findByBrand(brand));
    }

    @Override
    public BookShowDetailsDto findByIdAndAvailableTrue(Long id) {
        return bookMapper.toDto(bookRepository.findByIdAndAvailableTrue(id).orElseThrow(() -> new RuntimeException("Book Not Available")));
    }

    @Override
    public List<BookShowListDto> findAvailableFalse() {
        return bookMapper.toListDtos(bookRepository.findByAvailableFalse());
    }

    @Override
    public List<BookShowListDto> findAvailableTrue() {
        return bookMapper.toListDtos(bookRepository.findByAvailableTrue());
    }

    @Override
    public void createBook(BookShowDetailsDto book) {

        bookRepository.save(bookMapper.toEntity(book));
    }

    @Transactional
    @Override
    public void update(long id, BookShowDetailsDto updatedBook) {

        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book Not Available"));
        book.setProductCode(updatedBook.getProductCode());
        book.setIsbn(updatedBook.getIsbn());
        book.setTitle(updatedBook.getTitle());
        book.setBrand(updatedBook.getBrand());
        book.setYearOfPublication(updatedBook.getYearOfPublication());
        book.setLanguage(updatedBook.getLanguage());
        book.setPrice(updatedBook.getPrice());
        book.setAvailable(updatedBook.getAvailable());
        book.setDescription(updatedBook.getDescription());

        book.setLiteraryGenre(updatedBook.getLiteraryGenre());

        book.getAuthors().clear();
        Set<Author> authors = updatedBook.getAuthors().stream()
                .map(a -> authorRepository.findById(a.getId())
                        .orElseThrow(() -> new RuntimeException("Author not found")))
                .collect(Collectors.toSet());

        book.setAuthors(authors);

        book.getBookGenre().clear();
        Set<BookGenre> bookGenres = updatedBook.getBookGenre().stream()
                .map(b -> bookGenreRepository.findById(b.getId())
                        .orElseThrow(() -> new RuntimeException("Book genre not found!")))
                .collect(Collectors.toSet());

        book.setBookGenre(bookGenres);
    }


    @Override
    public void delete(Long id) {

        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found!"));

        book.getAuthors().clear();
        book.getBookGenre().clear();

        bookRepository.delete(book);
    }
}
