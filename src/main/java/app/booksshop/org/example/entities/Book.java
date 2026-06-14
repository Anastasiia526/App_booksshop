package app.booksshop.org.example.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_path")
    private String image;

    @Column(name = "product_code", unique = true)
    private long productCode;

    @Column(unique = true)
    private String isbn;

    @Column
    private String title;
    private String brand;

    @Column(name = "year_of_publication")
    private int yearOfPublication;
    private String language;
    private Double price;
    private boolean available;

    @Column(name = "descriptions", columnDefinition = "text")
    private String description;


    @ManyToMany
    @JoinTable(name = "books_info_authors",
            joinColumns = @JoinColumn(name = "books_info_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authors_books_id", referencedColumnName = "id"))
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "categories_by_books", joinColumns = @JoinColumn(name = "books_info_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_genres_id", referencedColumnName = "id"))
    private Set<BookGenre> bookGenre = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "literary_genres_id")
    private LiteraryGenre literaryGenre;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "booksOrder",
            joinColumns = @JoinColumn(name = "books_info_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "orders_id", referencedColumnName = "id"))
    private Set<Order> orders = new HashSet<>();

}

