package app.booksshop.org.example.services.interfaces;

import app.booksshop.org.example.dto.LiteraryGenreDto;
import app.booksshop.org.example.dto.LiteraryGenreListDto;

import java.util.List;

public interface LiteraryGenreServiceInterface {

List<LiteraryGenreListDto> getLiteraryGenreList();

LiteraryGenreDto findById(Long id);

List<LiteraryGenreListDto> getAll();
}
