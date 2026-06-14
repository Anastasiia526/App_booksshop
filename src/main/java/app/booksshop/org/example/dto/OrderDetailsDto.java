package app.booksshop.org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailsDto that = (OrderDetailsDto) o;
        return Objects.equals(id, that.id) && Objects.equals(books, that.books) && Objects.equals(customer, that.customer) && Objects.equals(delivery, that.delivery) && Objects.equals(pay, that.pay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, books, customer, delivery, pay);
    }
}
