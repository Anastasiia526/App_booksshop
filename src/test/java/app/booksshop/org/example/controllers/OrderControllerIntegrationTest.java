package app.booksshop.org.example.controllers;

import app.booksshop.org.example.dto.*;
import app.booksshop.org.example.entities.enums.DeliveryMethod;
import app.booksshop.org.example.services.interfaces.BookServiceInterface;
import app.booksshop.org.example.services.interfaces.OrderServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderServiceInterface orderService;

    @MockitoBean
    private BookServiceInterface bookService;

    @Test
    void ordersPageIsAvailableForAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/orders")
                        .with(user("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("booksshop/form-customer"));
    }

    @Test
    void addBookCreatesOrderWhenSessionOrderHasNoIdAndRedirectsToCustomerForm() throws Exception {
        OrderDetailsDto savedOrder = new OrderDetailsDto();
        savedOrder.setId(10L);

        BookShowDetailsDto book = new BookShowDetailsDto();
        book.setId(5L);
        book.setTitle("Integration Book");
        book.setAvailable(true);

        when(orderService.createOrder(any(OrderDetailsDto.class))).thenReturn(savedOrder);
        when(bookService.findByBookId(5L)).thenReturn(book);

        mockMvc.perform(post("/orders/add-book")
                        .param("bookId", "5")
                        .with(user("user").roles("USER"))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/customer"));

        verify(orderService).createOrder(any(OrderDetailsDto.class));
        verify(bookService).findByBookId(5L);
        verify(orderService).addBookToOrder(5L, 10L);
    }

    @Test
    void customerFormRedirectsToShopWhenOrderWasNotStarted() throws Exception {
        mockMvc.perform(get("/orders/customer")
                        .with(user("user").roles("USER")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/booksshop"));
    }

    @Test
    void submitCustomerWithValidDataUpdatesCustomerAndRedirectsToDelivery() throws Exception {
        OrderDetailsDto order = new OrderDetailsDto();
        order.setId(20L);
        order.setBooks(new HashSet<>());

        mockMvc.perform(post("/orders/customer")
                        .sessionAttr("order", order)
                        .param("customer.firstName", "Petro")
                        .param("customer.lastName", "Shevchenko")
                        .param("customer.email", "petro@example.com")
                        .param("customer.phoneNumber", "+380991234567")
                        .with(user("user").roles("USER"))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/delivery"));

        verify(orderService).updateCustomer(eq(20L), any());
    }

    @Test
    void submitCustomerWithInvalidDataReturnsCustomerForm() throws Exception {
        OrderDetailsDto order = new OrderDetailsDto();
        order.setId(20L);
        order.setBooks(new HashSet<>());
        order.setCustomer(new OrderCustomerDto());
        order.setDelivery(new OrderDeliveryDto());
        order.setPay(new OrderPayDto());

        mockMvc.perform(post("/orders/customer")
                        .sessionAttr("order", order)
                        .param("customer.firstName", "A")
                        .param("customer.lastName", "B")
                        .param("customer.email", "not-email")
                        .param("customer.phoneNumber", "123")
                        .with(user("user").roles("USER"))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/delivery"));

        verify(orderService).updateCustomer(eq(20L), any());
    }

    @Test
    void submitDeliveryWithValidDataUpdatesDeliveryAndRedirectsToPay() throws Exception {
        OrderDetailsDto order = new OrderDetailsDto();
        order.setId(30L);
        order.setBooks(new HashSet<>());

        mockMvc.perform(post("/orders/delivery")
                        .sessionAttr("order", order)
                        .param("delivery.city", "Kyiv")
                        .param("delivery.region", "Kyivska")
                        .param("delivery.method", DeliveryMethod.values()[0].name())
                        .param("delivery.address", "Main street 1")
                        .with(user("user").roles("USER"))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/pay"));

        verify(orderService).updateDelivery(eq(30L), any());
    }

    @Test
    void submitPayWithValidDataUpdatesPayAndRedirectsToCompletePage() throws Exception {
        OrderDetailsDto order = new OrderDetailsDto();
        order.setId(40L);
        order.setBooks(new HashSet<>());

        mockMvc.perform(post("/orders/pay")
                        .sessionAttr("order", order)
                        .param("pay.cardNumber", "1234567812345678")
                        .param("pay.expirationDate", "12/29")
                        .param("pay.cvv", "123")
                        .with(user("user").roles("USER"))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/complete/40"));

        verify(orderService).updatePay(eq(40L), any());
    }

    @Test
    void ordersPageRedirectsAnonymousUserToLogin() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/auth/login"));
    }
}
