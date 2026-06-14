package app.booksshop.org.example.controllers;

import app.booksshop.org.example.dto.BookGenreListDto;
import app.booksshop.org.example.dto.LiteraryGenreListDto;
import app.booksshop.org.example.services.interfaces.BookGenreServiceInterface;
import app.booksshop.org.example.services.interfaces.LiteraryGenreServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
@ActiveProfiles("test")
public class LiteraryGenreControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LiteraryGenreServiceInterface literaryGenreService;

    @MockitoBean
    private BookGenreServiceInterface bookGenreService;

    @Test
    @WithMockUser(roles = "USER")
    void startPage_whenUserAuthenticated_returnsStartPage() throws Exception {
        mockMvc.perform(get("/booksshop"))
                .andExpect(status().isOk())
                .andExpect(view().name("/booksshop/startPage"));

        verifyNoInteractions(literaryGenreService);
        verifyNoInteractions(bookGenreService);
    }

    @Test
    @WithMockUser(roles = "USER")
    void literaryGenreList_whenUserAuthenticated_returnsLiteraryGenreListPage() throws Exception {
        LiteraryGenreListDto literaryGenre = new LiteraryGenreListDto();
        literaryGenre.setId(1L);
        literaryGenre.setName("Fiction");

        when(literaryGenreService.getLiteraryGenreList()).thenReturn(List.of(literaryGenre));

        mockMvc.perform(get("/booksshop/literaryGenreList"))
                .andExpect(status().isOk())
                .andExpect(view().name("/booksshop/literaryGenreList"))
                .andExpect(model().attributeExists("literaryGenres"))
                .andExpect(model().attribute("literaryGenres",
                        hasItem(hasProperty("name", is("Fiction")))));

        verify(literaryGenreService).getLiteraryGenreList();
        verifyNoInteractions(bookGenreService);
    }

    @Test
    @WithMockUser(roles = "USER")
    void listBooksGenresByLiteraryGenre_whenUserAuthenticated_returnsBookGenreListPage() throws Exception {
        BookGenreListDto bookGenre = new BookGenreListDto();
        bookGenre.setId(1L);
        bookGenre.setName("Classical prose");

        when(bookGenreService.findBookGenresByLiteraryGenreId(1L)).thenReturn(List.of(bookGenre));

        mockMvc.perform(get("/booksshop/literaryGenres/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("booksshop/bookGenreList"))
                .andExpect(model().attributeExists("bookGenre"))
                .andExpect(model().attribute("bookGenre",
                        hasItem(hasProperty("name", is("Classical prose")))));

        verify(bookGenreService).findBookGenresByLiteraryGenreId(1L);
        verifyNoInteractions(literaryGenreService);
    }
}
