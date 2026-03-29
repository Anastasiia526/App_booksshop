package app.booksshop.org.example.services.mappers;

import app.booksshop.org.example.dto.AuthorShowDto;
import app.booksshop.org.example.entities.Author;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorShowDto toDto(Author author);
    List<AuthorShowDto> toDtos(List<Author> authors);
    Author toEntity(AuthorShowDto authorShowDto);

}
