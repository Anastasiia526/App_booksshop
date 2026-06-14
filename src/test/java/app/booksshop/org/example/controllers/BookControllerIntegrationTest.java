package app.booksshop.org.example.controllers;

import app.booksshop.org.example.dto.*;
import app.booksshop.org.example.services.interfaces.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
@ActiveProfiles("test")
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookServiceInterface bookService;

    @MockitoBean
    private BookGenreServiceInterface bookGenreService;

    @MockitoBean
    private LiteraryGenreServiceInterface literaryGenreService;

    @MockitoBean
    private AuthorServiceInterface authorService;

    @MockitoBean
    private FileService fileService;

    @Test
    @WithMockUser(roles = "USER")
    void showBookDetails_whenUserAuthenticated_returnsDetailsPage() throws Exception {
        BookShowDetailsDto book = new BookShowDetailsDto();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setIsbn("9780132350884");
        book.setProductCode(123456L);
        book.setYearOfPublication(2008);
        book.setPrice(45.99);
        book.setAvailable(true);

        when(bookService.findByBookId(1L)).thenReturn(book);

        mockMvc.perform(get("/booksshop/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("booksshop/bookDetails"))
                .andExpect(model().attribute("book", hasProperty("title", is("Clean Code"))));

        verify(bookService).findByBookId(1L);
    }

    @Test
    @WithMockUser(roles = "USER")
    void showBooksByLiteraryGenre_returnsLiteraryGenrePage() throws Exception {
        LiteraryGenreDto genre = new LiteraryGenreDto();
        genre.setId(10L);
        genre.setName("Technical Literature");

        BookShowListDto book = new BookShowListDto();
        book.setId(1L);
        book.setTitle("Spring in Action");

        when(literaryGenreService.findById(10L)).thenReturn(genre);
        when(bookService.findByLiteraryGenreId(10L)).thenReturn(List.of(book));

        mockMvc.perform(get("/booksshop/literaryGenre/10"))
                .andExpect(status().isOk())
                .andExpect(view().name("booksshop/bookListByLiteraryGenre"))
                .andExpect(model().attributeExists("genre"))
                .andExpect(model().attributeExists("books"));

        verify(literaryGenreService).findById(10L);
        verify(bookService).findByLiteraryGenreId(10L);
    }

    @Test
    @WithMockUser(roles = "USER")
    void showBooksByBookGenres_returnsBookGenrePage() throws Exception {
        BookGenreDto genre = new BookGenreDto();
        genre.setId(20L);
        genre.setName("Programming");

        BookShowListDto book = new BookShowListDto();
        book.setId(2L);
        book.setTitle("Effective Java");

        when(bookGenreService.findById(20L)).thenReturn(genre);
        when(bookService.findByBookGenreId(20L)).thenReturn(List.of(book));

        mockMvc.perform(get("/booksshop/genre/20"))
                .andExpect(status().isOk())
                .andExpect(view().name("booksshop/bookListByBookGenres"))
                .andExpect(model().attributeExists("genre"))
                .andExpect(model().attributeExists("books"));

        verify(bookGenreService).findById(20L);
        verify(bookService).findByBookGenreId(20L);
    }

    @Test
    void showBookDetails_whenAnonymous_returnsDetailsPageBecauseBookshopIsPublic() throws Exception {
        BookShowDetailsDto book = new BookShowDetailsDto();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setIsbn("9780132350884");
        book.setProductCode(123456L);
        book.setYearOfPublication(2008);
        book.setPrice(45.99);
        book.setAvailable(true);

        when(bookService.findByBookId(1L)).thenReturn(book);

        mockMvc.perform(get("/booksshop/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("booksshop/bookDetails"))
                .andExpect(model().attribute("book", hasProperty("title", is("Clean Code"))));

        verify(bookService).findByBookId(1L);
    }

}
