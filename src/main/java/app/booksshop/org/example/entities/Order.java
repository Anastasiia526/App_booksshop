package app.booksshop.org.example.entities;

import app.booksshop.org.example.entities.enums.DeliveryMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    private String email;

    @Column(name = "phone", nullable = false)
    private String phoneNumber;

    @Column(name = "delivery_City")
    private String city;

    @Column(name = "delivery_Region")
    private String region;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_Method")
    private DeliveryMethod method;

    @Column(name = "delivery_Address")
    private String address;

    @Column(name = "cc_number")
    private String cardNumber;

    @Column(name = "cc_expiration")
    private String expirationDate;

    @Column(name = "cc_cvv")
    private String cvv;

    @ManyToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

}
