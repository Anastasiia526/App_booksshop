package app.booksshop.org.example.services.mappers;

import app.booksshop.org.example.dto.BookGenreDto;
import app.booksshop.org.example.dto.BookGenreListDto;
import app.booksshop.org.example.entities.BookGenre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookGenreMapper {

    List<BookGenreListDto> toDtos(List<BookGenre> bookGenres);

    BookGenreListDto toListDto(BookGenre genre);

    BookGenreDto toDto(BookGenre bookGenre);

    BookGenre toEntity(BookGenreDto bookGenreDto);


}
