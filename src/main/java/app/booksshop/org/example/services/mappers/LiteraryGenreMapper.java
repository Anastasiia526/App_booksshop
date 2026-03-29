package app.booksshop.org.example.services.mappers;

import app.booksshop.org.example.dto.LiteraryGenreDto;
import app.booksshop.org.example.dto.LiteraryGenreListDto;
import app.booksshop.org.example.entities.LiteraryGenre;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LiteraryGenreMapper {

    LiteraryGenreDto toDto(LiteraryGenre genre);

    LiteraryGenre toEntity(LiteraryGenreDto toDto);

    LiteraryGenreListDto toListDto(LiteraryGenre genre);

    default List<LiteraryGenreListDto> toListDtos(List<LiteraryGenre> genres) {
        if (genres == null) return new ArrayList<>();
        List<LiteraryGenreListDto> list = new ArrayList<>();
        for (LiteraryGenre g : genres) {
            list.add(toListDto(g));
        }
        return list;
    }
}
