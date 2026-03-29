package app.booksshop.org.example.services.implementations;


import app.booksshop.org.example.dto.*;
import app.booksshop.org.example.entities.Book;
import app.booksshop.org.example.entities.Order;
import app.booksshop.org.example.entities.User;
import app.booksshop.org.example.repositories.BookRepository;
import app.booksshop.org.example.repositories.OrderRepository;
import app.booksshop.org.example.services.interfaces.OrderServiceInterface;
import app.booksshop.org.example.services.mappers.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
public class OrderServiceInterfaceImpl implements OrderServiceInterface {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceInterfaceImpl(OrderRepository orderRepository, BookRepository bookRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDetailsDto createOrder(OrderDetailsDto orderDto) {
        Order order = orderMapper.toOrderEntity(orderDto);

        if (orderDto.getBooks() != null) {
            for (BookShowDetailsDto bookDto : orderDto.getBooks()) {
                Book book = bookRepository.findById(bookDto.getId())
                        .orElseThrow();
                order.getBooks().add(book);
            }
        }

        Order saved = orderRepository.save(order);
        return orderMapper.toOrderDetailsDto(saved);
    }

    @Override
    public OrderDetailsDto addBookToOrder(Long bookId, Long orderId) {

        Book book = bookRepository.findByIdAndAvailableTrue(bookId)
                .orElseThrow(() -> new RuntimeException("Book not available"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getBooks() == null) {
            order.setBooks(new HashSet<>());
        }

        order.getBooks().add(book);
        book.getOrders().add(order);

        Order saveOrder = orderRepository.save(order);

        return orderMapper.toOrderDetailsDto(saveOrder);
    }

    @Override
    @Transactional
    public void updateCustomer(Long orderId, OrderCustomerDto dto) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            return;
        }
        Object principal = auth.getPrincipal();

        if(principal instanceof User user){
            order.setEmail(user.getUsername());//з security
        }else {
            order.setFirstName(dto.getEmail());
        }
        order.setFirstName(dto.getFirstName());
        order.setLastName(dto.getLastName());
        order.setEmail(dto.getEmail());
        order.setPhoneNumber(dto.getPhoneNumber());

        orderRepository.save(order);
    }

    @Override
    public void updateDelivery(Long orderId, OrderDeliveryDto dto) {
        Order  order = orderRepository.findById(orderId)
                .orElseThrow();

        order.setMethod(dto.getMethod());
        order.setCity(dto.getCity());
        order.setAddress(dto.getAddress());
        order.setRegion(dto.getRegion());

        orderRepository.save(order);
    }

    @Override
    public void updatePay(Long orderId, OrderPayDto dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow();

        order.setCardNumber(order.getCardNumber());
        order.setExpirationDate(order.getExpirationDate());
        order.setCvv(order.getCvv());

        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void completeOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getBooks() == null || order.getBooks().isEmpty()) {
            throw new IllegalStateException("Order has no books");
        }

        if (order.getFirstName() == null || order.getEmail() == null) {
            throw new IllegalStateException("Customer info is incomplete");
        }

        if (order.getAddress() == null) {
            throw new IllegalStateException("Delivery info is missing");
        }

        if (order.getCardNumber() == null) {
            throw new IllegalStateException("Payment info is missing");
        }

        orderRepository.save(order);
    }

    @Override
    public OrderDetailsDto getOrderDtoById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toOrderDetailsDto(order);
    }


}
