package app.booksshop.org.example.services.interfaces;


import app.booksshop.org.example.dto.OrderCustomerDto;
import app.booksshop.org.example.dto.OrderDeliveryDto;
import app.booksshop.org.example.dto.OrderDetailsDto;
import app.booksshop.org.example.dto.OrderPayDto;

public interface OrderServiceInterface {

    OrderDetailsDto createOrder(OrderDetailsDto orderDto);

    OrderDetailsDto addBookToOrder(Long bookId, Long orderId);


    void updateCustomer(Long orderId, OrderCustomerDto dto);

    void updateDelivery(Long orderId, OrderDeliveryDto dto);

    void updatePay(Long orderId, OrderPayDto dto);

    void completeOrder(Long id);

    OrderDetailsDto getOrderDtoById(Long orderId);
}
