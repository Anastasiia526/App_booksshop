package app.booksshop.org.example.controllers;


import app.booksshop.org.example.dto.*;
import app.booksshop.org.example.services.interfaces.*;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/booksshop")
public class BookController {

    private final BookServiceInterface bookService;
    private final BookGenreServiceInterface bookGenreService;
    private final LiteraryGenreServiceInterface literaryGenreService;
    private final AuthorServiceInterface authorService;
    private final FileService fileService;

    public BookController(BookServiceInterface bookService, BookGenreServiceInterface bookGenreService,
                          LiteraryGenreServiceInterface literaryGenreService,  AuthorServiceInterface authorService,
                          FileService fileService) {
        this.bookService = bookService;
        this.bookGenreService = bookGenreService;
        this.literaryGenreService = literaryGenreService;
        this.authorService = authorService;
        this.fileService = fileService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/genre/{id}/book")
    public String showBooksByGenre(@PathVariable Long id, Model model) {
        BookGenreDto genre = bookGenreService.findById(id);
        Set<BookShowListDto> books = new HashSet<>(genre.getBooks());
        model.addAttribute("books", books);
        model.addAttribute("genre", genre);
        return "booksshop/bookListByBookGenres";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public String showBookDetails(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findByBookId(id));
        return "booksshop/bookDetails";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/literaryGenre/{id}")
    public String showBooksByLiteraryGenre(@PathVariable("id") Long id, Model model) {
        LiteraryGenreDto literaryGenre = literaryGenreService.findById(id);
        List<BookShowListDto> books = bookService.findByLiteraryGenreId(id);
        model.addAttribute("genre", literaryGenre);
        model.addAttribute("books", books);
        return "booksshop/bookListByLiteraryGenre";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/genre/{id}")
    public String showBooksByBookGenres(@PathVariable("id") Long id, Model model) {
        BookGenreDto genre = bookGenreService.findById(id);
        List<BookShowListDto> books = bookService.findByBookGenreId(id);
        model.addAttribute("genre", genre);
        model.addAttribute("books", books);
        return "booksshop/bookListByBookGenres";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/brand/{brand}")
    public String showBooksByBrand(@PathVariable String brand, Model model) {
        List<BookShowBrandDto> books = bookService.findByBrand(brand);
        model.addAttribute("books", books);
        model.addAttribute("brand", brand);
        return "booksshop/bookListByBrand";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/unavailable")
    public String showBooksUnavailable(Model model) {
        List<BookShowListDto> books = bookService.findAvailableFalse();
        model.addAttribute("books", books);
        return "booksshop/unavailableBooks";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/available")
    public String showBooksAvailable(Model model) {
        List<BookShowListDto> books = bookService.findAvailableTrue();
        model.addAttribute("books", books);
        return "booksshop/availableBooks";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/book/new")
    public String newBook(@ModelAttribute("book") BookShowDetailsDto book, Model model) {
        model.addAttribute("book", new BookShowDetailsDto());
        model.addAttribute("allAuthors", authorService.findAll());
        model.addAttribute("allBookGenres", bookGenreService.findAll());
        model.addAttribute("allLiteraryGenres", literaryGenreService.getAll());
        return "booksshop/new";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/book/new")
    public String create(@ModelAttribute("book") @Valid BookShowDetailsDto book,
                         BindingResult bindingResult, @RequestParam("file") MultipartFile file,
                         Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allAuthors", authorService.findAll());
            model.addAttribute("allBookGenres", bookGenreService.findAll());
            model.addAttribute("allLiteraryGenres", literaryGenreService.getAll());
            return "booksshop/new";
        }

        String fileName = fileService.saveFile(file);
        book.setImage(fileName);

        bookService.createBook(book);
        return "redirect:/booksshop";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") Long id) {
        BookShowDetailsDto book = bookService.findByBookId(id);
        model.addAttribute("book", book);
        model.addAttribute("allAuthors", authorService.findAll());
        model.addAttribute("allBookGenres", bookGenreService.findAll());
        model.addAttribute("allLiteraryGenres", literaryGenreService.getAll());
        return "booksshop/editBook";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/{id}/edit")
    public String update(@ModelAttribute("book") @Valid BookShowDetailsDto book, BindingResult bindingResult,
                         @PathVariable("id") Long id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allAuthors", authorService.findAll());
            model.addAttribute("allBookGenres", bookGenreService.findAll());
            model.addAttribute("allLiteraryGenres", literaryGenreService.getLiteraryGenreList());
            System.out.println(bindingResult.getAllErrors());
            return "booksshop/editBook";
        }
        bookService.update(id, book);
        return "booksshop/success";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "booksshop/success";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/booksshop")
    public String buttonHome() {
        return "booksshop/home";
    }
}
