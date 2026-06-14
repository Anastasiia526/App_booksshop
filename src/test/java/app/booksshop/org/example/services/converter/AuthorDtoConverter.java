package app.booksshop.org.example.services.converter;

import app.booksshop.org.example.dto.AuthorShowDto;
import app.booksshop.org.example.entities.Author;
import app.booksshop.org.example.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorDtoConverterTest {

    @Mock
    private AuthorRepository authorRepository;

    @Test
    void convert_whenSourceIsNull_returnsNull() {
        AuthorDtoConverter converter = new AuthorDtoConverter(authorRepository);

        AuthorShowDto result = converter.convert(null);

        assertThat(result).isNull();
        verifyNoInteractions(authorRepository);
    }

    @Test
    void convert_whenSourceIsBlank_returnsNull() {
        AuthorDtoConverter converter = new AuthorDtoConverter(authorRepository);

        AuthorShowDto result = converter.convert("   ");

        assertThat(result).isNull();
        verifyNoInteractions(authorRepository);
    }

    @Test
    void convert_whenSourceIsNotNumber_throwsRuntimeException() {
        AuthorDtoConverter converter = new AuthorDtoConverter(authorRepository);

        assertThatThrownBy(() -> converter.convert("abc"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Invalid author id: abc");

        verifyNoInteractions(authorRepository);
    }

    @Test
    void convert_whenAuthorDoesNotExist_throwsRuntimeException() {
        AuthorDtoConverter converter = new AuthorDtoConverter(authorRepository);

        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> converter.convert("99"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Author not found: 99");

        verify(authorRepository).findById(99L);
    }

    @Test
    void convert_whenAuthorExists_returnsDto() {
        AuthorDtoConverter converter = new AuthorDtoConverter(authorRepository);

        Author author = new Author();
        author.setId(1L);
        author.setFullName("Joshua Bloch");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        AuthorShowDto result = converter.convert("1");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFullName()).isEqualTo("Joshua Bloch");

        verify(authorRepository).findById(1L);
    }
}
