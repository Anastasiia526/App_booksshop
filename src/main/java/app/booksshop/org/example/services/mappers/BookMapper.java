package app.booksshop.org.example.services.mappers;

import app.booksshop.org.example.dto.BookShowBrandDto;
import app.booksshop.org.example.dto.BookShowDetailsDto;
import app.booksshop.org.example.dto.BookShowListDto;
import app.booksshop.org.example.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    List<BookShowListDto> toListDtos(List<Book> books);

    BookShowDetailsDto toDto(Book book);

    Book toEntity(BookShowDetailsDto bookShowDetailsDto);

    BookShowListDto map(Book value);

    BookShowBrandDto mapByBrand(Book value);

    List<BookShowBrandDto> toDtos(List<Book> books);

    void updateBookFromDto(BookShowDetailsDto dto, @MappingTarget Book entity);
}
