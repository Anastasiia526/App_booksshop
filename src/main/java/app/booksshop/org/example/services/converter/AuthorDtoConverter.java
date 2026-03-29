package app.booksshop.org.example.services.converter;

import app.booksshop.org.example.dto.AuthorShowDto;
import app.booksshop.org.example.entities.Author;
import app.booksshop.org.example.repositories.AuthorRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class AuthorDtoConverter implements Converter<String, AuthorShowDto> {

    private final AuthorRepository authorRepository;

    public AuthorDtoConverter(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorShowDto convert(String source) {

        if (source == null || source.isBlank()) {
            return null;
        }

        Long id;

        try {
            id = Long.parseLong(source);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid author id: " + source);
        }

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found: " + id));

        AuthorShowDto dto = new AuthorShowDto();
        dto.setId(author.getId());
        dto.setFullName(author.getFullName());

        return dto;
    }
}
