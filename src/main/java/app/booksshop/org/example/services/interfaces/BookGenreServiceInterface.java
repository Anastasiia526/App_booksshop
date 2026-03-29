package app.booksshop.org.example.services.interfaces;

import app.booksshop.org.example.dto.BookGenreDto;
import app.booksshop.org.example.dto.BookGenreListDto;

import java.util.List;

public interface BookGenreServiceInterface{

//    List<BookGenreListDto> getBookGenreList();

    List<BookGenreListDto> findBookGenresByLiteraryGenreId(Long id);

    BookGenreDto findById(Long id);

    List<BookGenreListDto> findAll();


}
