package app.booksshop.org.example.services.implementations;


import app.booksshop.org.example.dto.LiteraryGenreDto;
import app.booksshop.org.example.dto.LiteraryGenreListDto;
import app.booksshop.org.example.repositories.LiteraryGenreRepository;
import app.booksshop.org.example.services.interfaces.LiteraryGenreServiceInterface;
import app.booksshop.org.example.services.mappers.LiteraryGenreMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LiteraryGenreServiceImpl implements LiteraryGenreServiceInterface {

    private final LiteraryGenreRepository literaryGenreRepository;
    private final LiteraryGenreMapper literaryGenreMapper;

    public LiteraryGenreServiceImpl(LiteraryGenreRepository literaryGenreRepository, LiteraryGenreMapper literaryGenreMapper) {
        this.literaryGenreRepository = literaryGenreRepository;
        this.literaryGenreMapper = literaryGenreMapper;
    }


    @Override
    public List<LiteraryGenreListDto> getLiteraryGenreList() {
        return literaryGenreMapper.toListDtos(literaryGenreRepository.findAll());
    }

    @Override
    public LiteraryGenreDto findById(Long id) {
        return literaryGenreRepository.findById(id).map(literaryGenreMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Literary Genre Not Found"));
    }

    @Override
    public List<LiteraryGenreListDto> getAll() {
        return literaryGenreMapper.toListDtos(literaryGenreRepository.findAll());
    }
}
