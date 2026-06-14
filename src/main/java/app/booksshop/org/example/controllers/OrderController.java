package app.booksshop.org.example.controllers;

import app.booksshop.org.example.dto.*;
import app.booksshop.org.example.services.interfaces.BookServiceInterface;
import app.booksshop.org.example.services.interfaces.OrderServiceInterface;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.HashSet;


@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private final OrderServiceInterface orderService;
    private final BookServiceInterface bookService;

    @Autowired
    public OrderController(OrderServiceInterface orderService, BookServiceInterface bookService) {
        this.orderService = orderService;
        this.bookService = bookService;
    }

    @ModelAttribute("order")
    public OrderDetailsDto order() {
        OrderDetailsDto order = new OrderDetailsDto();
        order.setBooks(new HashSet<>());
        order.setCustomer(new OrderCustomerDto());
        order.setDelivery(new OrderDeliveryDto());
        order.setPay(new OrderPayDto());
        return order;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public String showForm() {
        return "booksshop/form-customer";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/add-book")
    public String addBook(@RequestParam Long bookId,
                          @ModelAttribute("order") OrderDetailsDto order) {

        if (order.getId() == null) {
            OrderDetailsDto saved = orderService.createOrder(order);
            order.setId(saved.getId());
            log.info("CREATED ORDER ID: {}", saved.getId());
        }

        BookShowDetailsDto book = bookService.findByBookId(bookId);

        order.getBooks().add(book);
        orderService.addBookToOrder(bookId, order.getId());

        return "redirect:/orders/customer";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/customer")
    public String showCustomerForm(@ModelAttribute("order") OrderDetailsDto order) {
        if(order.getId() == null){
            return "redirect:/booksshop";
        }
        return "booksshop/form-customer";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/customer")
    public String submitCustomer(@Valid @ModelAttribute("order") OrderDetailsDto order,
                                 BindingResult bindingResult) {
        if(order.getId() == null){
           return "redirect:/booksshop";
        }
        if (bindingResult.hasErrors()) {
            return "booksshop/form-customer";
        }
        orderService.updateCustomer(order.getId(), order.getCustomer());

        return "redirect:/orders/delivery";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/delivery")
    public String delivery(@ModelAttribute("order") OrderDetailsDto order) {
        return "booksshop/form-delivery";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/delivery")
    public String delivery(@Valid@ModelAttribute("order") OrderDetailsDto orderDto,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "booksshop/form-delivery";
        }

        orderService.updateDelivery(orderDto.getId(), orderDto.getDelivery());

        return "redirect:/orders/pay";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/pay")
    public String pay(@ModelAttribute("order") OrderDetailsDto order) {

        return "booksshop/form-pay";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/pay")
    public String pay(@Valid@ModelAttribute("order") OrderDetailsDto order,
                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "booksshop/form-pay";
        }

        orderService.updatePay(order.getId(), order.getPay());
        return "redirect:/orders/complete/" + order.getId();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/complete/{orderId}")
    public String complete(@PathVariable Long orderId, SessionStatus sessionStatus, Model model) {

        orderService.completeOrder(orderId);
        OrderDetailsDto order = orderService.getOrderDtoById(orderId);
        model.addAttribute("order", order);
        sessionStatus.setComplete();
        return "booksshop/order-complete";
    }

}