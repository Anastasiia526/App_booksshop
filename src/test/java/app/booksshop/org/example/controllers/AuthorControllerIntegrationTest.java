package app.booksshop.org.example.controllers;

import app.booksshop.org.example.dto.AuthorShowDto;
import app.booksshop.org.example.dto.BookShowListDto;
import app.booksshop.org.example.services.interfaces.AuthorServiceInterface;
import app.booksshop.org.example.services.interfaces.BookServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
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
class AuthorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorServiceInterface authorService;

    @MockitoBean
    private BookServiceInterface bookService;

    @Test
    @WithMockUser(roles = "USER")
    void showBooksByAuthors_whenUserAuthenticated_returnsBooksPage() throws Exception {
        AuthorShowDto author = new AuthorShowDto();
        author.setId(1L);
        author.setFullName("Robert Martin");

        BookShowListDto book = new BookShowListDto();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setImage("book1.jpg");

        when(authorService.findById(1L)).thenReturn(author);
        when(bookService.findByAuthorId(1L)).thenReturn(List.of(book));

        mockMvc.perform(get("/booksshop/author/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("booksshop/bookListByAuthor"))
                .andExpect(model().attributeExists("author"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("author", hasProperty("fullName", is("Robert Martin"))));

        verify(authorService).findById(1L);
        verify(bookService).findByAuthorId(1L);
    }

    @Test
    void showBooksByAuthors_whenAnonymous_redirectsToLogin() throws Exception {
        AuthorShowDto author = new AuthorShowDto();
        author.setId(1L);
        author.setFullName("Robert Martin");

        BookShowListDto book = new BookShowListDto();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setImage("book1.jpg");

        when(authorService.findById(1L)).thenReturn(author);
        when(bookService.findByAuthorId(1L)).thenReturn(List.of(book));

        mockMvc.perform(get("/booksshop/author/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("booksshop/bookListByAuthor"))
                .andExpect(model().attributeExists("author"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("author", hasProperty("fullName", is("Robert Martin"))));

        verify(authorService).findById(1L);
        verify(bookService).findByAuthorId(1L);
    }
}