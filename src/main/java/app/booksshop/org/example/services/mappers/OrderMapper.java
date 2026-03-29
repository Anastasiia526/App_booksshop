package app.booksshop.org.example.services.mappers;

import app.booksshop.org.example.dto.OrderDetailsDto;
import app.booksshop.org.example.entities.Book;
import app.booksshop.org.example.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "customer", source = ".")  // передаємо всю Order для customer
    @Mapping(target = "delivery", source = ".") // передаємо всю Order для delivery
    @Mapping(target = "pay", source = ".")      // передаємо всю Order для pay
    OrderDetailsDto toOrderDetailsDto(Order order);

    @Mapping(target = "lastName", source = "customer.lastName")
    @Mapping(target = "firstName", source = "customer.firstName")
    @Mapping(target = "email", source = "customer.email")
    @Mapping(target = "phoneNumber", source = "customer.phoneNumber")
    @Mapping(target = "city", source = "delivery.city")
    @Mapping(target = "region", source = "delivery.region")
    @Mapping(target = "method", source = "delivery.method")
    @Mapping(target = "address", source = "delivery.address")
    @Mapping(target = "cardNumber", source = "pay.cardNumber")
    @Mapping(target = "expirationDate", source = "pay.expirationDate")
    @Mapping(target = "cvv", source = "pay.cvv")
    @Mapping(target = "books", source = "books")
    Order toOrderEntity(OrderDetailsDto dto);

    // --- Маппінг Book Set (якщо потрібен) ---
    Set<Book> map(Set<Book> books);

}
