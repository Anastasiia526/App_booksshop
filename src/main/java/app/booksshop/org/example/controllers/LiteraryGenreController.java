package app.booksshop.org.example.controllers;


import app.booksshop.org.example.dto.BookGenreListDto;
import app.booksshop.org.example.services.interfaces.BookGenreServiceInterface;
import app.booksshop.org.example.services.interfaces.LiteraryGenreServiceInterface;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/booksshop")
public class LiteraryGenreController {

    private final LiteraryGenreServiceInterface literaryGenreService;
    private final BookGenreServiceInterface bookGenreService;

    public LiteraryGenreController(LiteraryGenreServiceInterface literaryGenreService, BookGenreServiceInterface bookGenreService) {
        this.literaryGenreService = literaryGenreService;
        this.bookGenreService = bookGenreService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public String startPage() {
        return "/booksshop/startPage";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/literaryGenreList")
    public String literaryGenreList(Model model) {
        model.addAttribute("literaryGenres", literaryGenreService.getLiteraryGenreList());
        return "/booksshop/literaryGenreList";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("literaryGenres/{id}")
    public String listBooksGenresByLiteraryGenre(@PathVariable("id") Long id, Model model) {
        List<BookGenreListDto> bookGenres = bookGenreService.findBookGenresByLiteraryGenreId(id);
        model.addAttribute("bookGenre", bookGenres);
        return "booksshop/bookGenreList";
    }
}
