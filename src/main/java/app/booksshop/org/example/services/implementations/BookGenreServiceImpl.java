package app.booksshop.org.example.services.implementations;

import app.booksshop.org.example.dto.BookGenreDto;
import app.booksshop.org.example.dto.BookGenreListDto;
import app.booksshop.org.example.repositories.BookGenreRepository;
import app.booksshop.org.example.services.interfaces.BookGenreServiceInterface;
import app.booksshop.org.example.services.mappers.BookGenreMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookGenreServiceImpl implements BookGenreServiceInterface {

    private final BookGenreRepository bookGenreRepository;
    private final BookGenreMapper bookGenreMapper;

    public BookGenreServiceImpl(BookGenreRepository bookGenreRepository,  BookGenreMapper bookGenreMapper) {
        this.bookGenreRepository = bookGenreRepository;
        this.bookGenreMapper = bookGenreMapper;
    }

    @Override
    public List<BookGenreListDto> findAll(){
    return bookGenreMapper.toDtos(bookGenreRepository.findAll());
    }

    @Override
    public List<BookGenreListDto> findBookGenresByLiteraryGenreId(Long id) {
        return bookGenreMapper.toDtos(bookGenreRepository.findBookGenresByLiteraryGenreId(id));
    }

    @Override
    public BookGenreDto findById(Long id) {
        return bookGenreMapper.toDto(bookGenreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book Genre Not Found!")));
    }

}
