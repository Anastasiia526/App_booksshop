package app.booksshop.org.example.services.interfaces;

import app.booksshop.org.example.dto.AuthorShowDto;

import java.util.List;

public interface AuthorServiceInterface {

    AuthorShowDto findById(long id);

    List<AuthorShowDto> findAll();
}
