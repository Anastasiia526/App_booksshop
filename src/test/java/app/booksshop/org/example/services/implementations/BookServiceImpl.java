package app.booksshop.org.example.services.implementations;

import app.booksshop.org.example.dto.AuthorShowDto;
import app.booksshop.org.example.dto.BookGenreListDto;
import app.booksshop.org.example.dto.BookShowDetailsDto;
import app.booksshop.org.example.dto.BookShowListDto;
import app.booksshop.org.example.entities.Author;
import app.booksshop.org.example.entities.Book;
import app.booksshop.org.example.entities.BookGenre;
import app.booksshop.org.example.repositories.AuthorRepository;
import app.booksshop.org.example.repositories.BookGenreRepository;
import app.booksshop.org.example.repositories.BookRepository;
import app.booksshop.org.example.services.mappers.BookMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookGenreRepository bookGenreRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void findByBookId_whenBookExists_returnsMappedDto() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");

        BookShowDetailsDto dto = new BookShowDetailsDto();
        dto.setId(1L);
        dto.setTitle("Clean Code");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(dto);

        BookShowDetailsDto result = bookService.findByBookId(1L);

        assertThat(result).isSameAs(dto);
        assertThat(result.getTitle()).isEqualTo("Clean Code");

        verify(bookRepository).findById(1L);
        verify(bookMapper).toDto(book);
    }

    @Test
    void findByBookId_whenBookDoesNotExist_throwsEntityNotFoundException() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findByBookId(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Book with id 99 not found");

        verify(bookRepository).findById(99L);
        verifyNoInteractions(bookMapper);
    }

    @Test
    void findAvailableTrue_returnsMappedDtos() {
        Book book = new Book();
        book.setId(1L);
        book.setAvailable(true);

        BookShowListDto dto = new BookShowListDto();
        dto.setId(1L);

        when(bookRepository.findByAvailableTrue()).thenReturn(List.of(book));
        when(bookMapper.toListDtos(List.of(book))).thenReturn(List.of(dto));

        List<BookShowListDto> result = bookService.findAvailableTrue();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getId()).isEqualTo(1L);

        verify(bookRepository).findByAvailableTrue();
        verify(bookMapper).toListDtos(List.of(book));
    }

    @Test
    void createBook_mapsDtoAndSavesEntity() {
        BookShowDetailsDto dto = new BookShowDetailsDto();
        dto.setTitle("Domain-Driven Design");

        Book entity = new Book();
        entity.setTitle("Domain-Driven Design");

        when(bookMapper.toEntity(dto)).thenReturn(entity);

        bookService.createBook(dto);

        verify(bookMapper).toEntity(dto);
        verify(bookRepository).save(entity);
    }

    @Test
    void update_whenBookExists_updatesFieldsAndRelations() {
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setProductCode(100L);
        existingBook.setIsbn("old-isbn");
        existingBook.setTitle("Old title");
        existingBook.setPrice(Double.valueOf(10));
        existingBook.getAuthors().add(new Author());
        existingBook.getBookGenre().add(new BookGenre());

        AuthorShowDto authorDto = new AuthorShowDto();
        authorDto.setId(11L);

        BookGenreListDto genreDto = new BookGenreListDto();
        genreDto.setId(22L);

        BookShowDetailsDto updatedDto = new BookShowDetailsDto();
        updatedDto.setProductCode(200L);
        updatedDto.setIsbn("new-isbn");
        updatedDto.setTitle("New title");
        updatedDto.setBrand("New brand");
        updatedDto.setYearOfPublication(2024);
        updatedDto.setLanguage("English");
        updatedDto.setPrice(25.50);
        updatedDto.setAvailable(true);
        updatedDto.setDescription("New description");
        updatedDto.setAuthors(Set.of(authorDto));
        updatedDto.setBookGenre(Set.of(genreDto));

        Author author = new Author();
        author.setId(11L);
        author.setFullName("Robert Martin");

        BookGenre bookGenre = new BookGenre();
        bookGenre.setId(22L);
        bookGenre.setName("Programming");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(authorRepository.findById(11L)).thenReturn(Optional.of(author));
        when(bookGenreRepository.findById(22L)).thenReturn(Optional.of(bookGenre));

        bookService.update(1L, updatedDto);

        assertThat(existingBook.getProductCode()).isEqualTo(200L);
        assertThat(existingBook.getIsbn()).isEqualTo("new-isbn");
        assertThat(existingBook.getTitle()).isEqualTo("New title");
        assertThat(existingBook.getBrand()).isEqualTo("New brand");
        assertThat(existingBook.getYearOfPublication()).isEqualTo(2024);
        assertThat(existingBook.getLanguage()).isEqualTo("English");
        assertThat(existingBook.getPrice()).isEqualByComparingTo(Double.valueOf(25.50));
        assertThat(existingBook.isAvailable()).isTrue();
        assertThat(existingBook.getDescription()).isEqualTo("New description");
        assertThat(existingBook.getAuthors()).containsExactly(author);
        assertThat(existingBook.getBookGenre()).containsExactly(bookGenre);

        verify(bookRepository).findById(1L);
        verify(authorRepository).findById(11L);
        verify(bookGenreRepository).findById(22L);
    }

    @Test
    void update_whenBookDoesNotExist_throwsRuntimeException() {
        BookShowDetailsDto dto = new BookShowDetailsDto();

        when(bookRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.update(404L, dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Book Not Available");

        verify(bookRepository).findById(404L);
        verifyNoInteractions(authorRepository, bookGenreRepository);
    }

    @Test
    void delete_whenBookExists_clearsRelationsAndDeletesBook() {
        Book book = new Book();
        book.setId(1L);

        Author author = new Author();
        author.setId(10L);

        BookGenre genre = new BookGenre();
        genre.setId(20L);

        book.getAuthors().add(author);
        book.getBookGenre().add(genre);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.delete(1L);

        assertThat(book.getAuthors()).isEmpty();
        assertThat(book.getBookGenre()).isEmpty();

        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).delete(bookCaptor.capture());
        assertThat(bookCaptor.getValue()).isSameAs(book);
    }

    @Test
    void delete_whenBookDoesNotExist_throwsRuntimeException() {
        when(bookRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.delete(404L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Book not found!");

        verify(bookRepository).findById(404L);
        verify(bookRepository, never()).delete(any());
    }
}
