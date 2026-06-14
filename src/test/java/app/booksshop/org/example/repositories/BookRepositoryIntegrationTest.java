package app.booksshop.org.example.repositories;

import app.booksshop.org.example.entities.Author;
import app.booksshop.org.example.entities.Book;
import app.booksshop.org.example.entities.BookGenre;
import app.booksshop.org.example.entities.LiteraryGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookRepositoryIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookGenreRepository bookGenreRepository;

    @Autowired
    private LiteraryGenreRepository literaryGenreRepository;

    @Test
    void findByAvailableTrue_returnsOnlyAvailableBooks() {
        Book availableBook = createBook("Clean Code", "9780132350884", true);
        Book unavailableBook = createBook("Refactoring", "9780201485677", false);

        bookRepository.save(availableBook);
        bookRepository.save(unavailableBook);

        List<Book> result = bookRepository.findByAvailableTrue();

        assertThat(result)
                .extracting(Book::getTitle)
                .contains("Clean Code")
                .doesNotContain("Refactoring");
    }

    @Test
    void findByAvailableFalse_returnsOnlyUnavailableBooks() {
        Book availableBook = createBook("Clean Architecture", "9780134494166", true);
        Book unavailableBook = createBook("Effective Java", "9780134685991", false);

        bookRepository.save(availableBook);
        bookRepository.save(unavailableBook);

        List<Book> result = bookRepository.findByAvailableFalse();

        assertThat(result)
                .extracting(Book::getTitle)
                .contains("Effective Java")
                .doesNotContain("Clean Architecture");
    }

    @Test
    void findByBrand_returnsBooksByBrand() {
        Book firstBook = createBook("Book One", "isbn-001", true);
        firstBook.setBrand("Pearson");

        Book secondBook = createBook("Book Two", "isbn-002", true);
        secondBook.setBrand("Manning");

        bookRepository.save(firstBook);
        bookRepository.save(secondBook);

        List<Book> result = bookRepository.findByBrand("Pearson");

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getTitle()).isEqualTo("Book One");
    }

    @Test
    void findByIdAndAvailableTrue_whenBookIsAvailable_returnsBook() {
        Book book = createBook("Available Book", "isbn-003", true);
        Book savedBook = bookRepository.save(book);

        Optional<Book> result = bookRepository.findByIdAndAvailableTrue(savedBook.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Available Book");
    }

    @Test
    void findByIdAndAvailableTrue_whenBookIsUnavailable_returnsEmptyOptional() {
        Book book = createBook("Unavailable Book", "isbn-004", false);
        Book savedBook = bookRepository.save(book);

        Optional<Book> result = bookRepository.findByIdAndAvailableTrue(savedBook.getId());

        assertThat(result).isEmpty();
    }

    @Test
    void findDistinctByAuthorsId_returnsBooksByAuthor() {
        Author author = new Author();
        author.setFullName("Joshua Bloch");
        Author savedAuthor = authorRepository.save(author);

        Book book = createBook("Effective Java", "isbn-005", true);
        book.getAuthors().add(savedAuthor);

        bookRepository.save(book);

        List<Book> result = bookRepository.findDistinctByAuthors_Id(savedAuthor.getId());

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getTitle()).isEqualTo("Effective Java");
    }

    @Test
    void findByBookGenreId_returnsBooksByGenre() {
        BookGenre genre = new BookGenre();
        genre.setName("Programming");
        BookGenre savedGenre = bookGenreRepository.save(genre);

        Book book = createBook("Java Concurrency in Practice", "isbn-006", true);
        book.getBookGenre().add(savedGenre);

        bookRepository.save(book);

        List<Book> result = bookRepository.findByBookGenreId(savedGenre.getId());

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getTitle()).isEqualTo("Java Concurrency in Practice");
    }

    @Test
    void findBooksByLiteraryGenreId_returnsBooksByLiteraryGenre() {
        LiteraryGenre literaryGenre = new LiteraryGenre();
        literaryGenre.setName("Technical Literature");
        LiteraryGenre savedLiteraryGenre = literaryGenreRepository.save(literaryGenre);

        Book book = createBook("Spring in Action", "isbn-007", true);
        book.setLiteraryGenre(savedLiteraryGenre);

        bookRepository.save(book);

        List<Book> result = bookRepository.findBooksByLiteraryGenreId(savedLiteraryGenre.getId());

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getTitle()).isEqualTo("Spring in Action");
    }

    private Book createBook(String title, String isbn, boolean available) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setProductCode(Math.abs(isbn.hashCode()));
        book.setBrand("Default brand");
        book.setYearOfPublication(2020);
        book.setLanguage("English");
        book.setPrice(Double.valueOf(39.99));
        book.setAvailable(available);
        book.setDescription("Test description");
        return book;
    }
}
