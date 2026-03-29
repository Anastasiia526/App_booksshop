package app.booksshop.org.example.dto;

import app.booksshop.org.example.entities.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {

    private Long id;
    private Set<BookShowDetailsDto> books = new HashSet<>();
    private OrderCustomerDto customer = new OrderCustomerDto();
    private OrderDeliveryDto delivery =  new OrderDeliveryDto();
    private OrderPayDto pay = new OrderPayDto();
}
