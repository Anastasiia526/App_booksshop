package app.booksshop.org.example.services.converter;

import app.booksshop.org.example.dto.BookGenreListDto;
import app.booksshop.org.example.entities.BookGenre;
import app.booksshop.org.example.repositories.BookGenreRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookGenreDtoConverter implements Converter<String, BookGenreListDto> {

    private final BookGenreRepository bookGenreRepository;

    public BookGenreDtoConverter(BookGenreRepository bookGenreRepository) {
        this.bookGenreRepository = bookGenreRepository;
    }

    @Override
    public BookGenreListDto convert(String source) {

        if (source == null || source.isBlank()) {
            return null;
        }

        Long id;
        try {
            id = Long.parseLong(source);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid genre id: " + source);
        }

        BookGenre genre = bookGenreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found: " + id));

        BookGenreListDto dto = new BookGenreListDto();
        dto.setId(genre.getId());
        dto.setName(genre.getName());

        return dto;
    }
}
