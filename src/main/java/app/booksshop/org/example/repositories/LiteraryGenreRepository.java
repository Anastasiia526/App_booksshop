package app.booksshop.org.example.repositories;

import app.booksshop.org.example.entities.LiteraryGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiteraryGenreRepository extends JpaRepository<LiteraryGenre,Long> {
}
