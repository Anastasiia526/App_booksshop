package app.booksshop.org.example.controllers;

import app.booksshop.org.example.dto.AuthorShowDto;
import app.booksshop.org.example.dto.BookShowListDto;
import app.booksshop.org.example.services.interfaces.AuthorServiceInterface;
import app.booksshop.org.example.services.interfaces.BookServiceInterface;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/booksshop")
public class AuthorController {

    private final AuthorServiceInterface authorService;
    private final BookServiceInterface bookService;


    public AuthorController(AuthorServiceInterface authorService, BookServiceInterface bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/author/{id}")
    public String showBooksByAuthors(@PathVariable("id")Long id, Model model) {
        AuthorShowDto author = authorService.findById(id);
        List<BookShowListDto> books = bookService.findByAuthorId(id);
        model.addAttribute("author", author);
        model.addAttribute("books", books);
        return "booksshop/bookListByAuthor";
    }
}
