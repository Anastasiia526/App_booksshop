package app.booksshop.org.example.services.implementations;

import app.booksshop.org.example.dto.AuthorShowDto;
import app.booksshop.org.example.repositories.AuthorRepository;
import app.booksshop.org.example.services.interfaces.AuthorServiceInterface;
import app.booksshop.org.example.services.mappers.AuthorMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthorServiceImpl implements AuthorServiceInterface {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public AuthorShowDto findById(long id) {
        return authorMapper.toDto(authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found")));
    }

    @Override
    public List<AuthorShowDto> findAll() {
        return authorMapper.toDtos(authorRepository.findAll());
    }
}
