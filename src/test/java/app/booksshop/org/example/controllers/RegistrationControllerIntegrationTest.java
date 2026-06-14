package app.booksshop.org.example.controllers;

import app.booksshop.org.example.entities.User;
import app.booksshop.org.example.services.implementations.RegistrationServiceImpl;
import app.booksshop.org.example.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Errors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RegistrationControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegistrationServiceImpl registrationService;

    @MockitoBean
    private UserValidator userValidator;

    @Test
    void loginPageIsPublic() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("booksshop/auth/login"));
    }

    @Test
    void registrationPageIsPublic() throws Exception {
        mockMvc.perform(get("/auth/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("booksshop/auth/registration"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void registrationWithValidDataRegistersUserAndRedirectsToLogin() throws Exception {
        mockMvc.perform(post("/auth/registration")
                        .param("username", "new-user")
                        .param("password", "password123")
                        .param("email", "new-user@example.com")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/login"));

        verify(userValidator).validate(any(User.class), any(Errors.class));
        verify(registrationService).register(any(User.class));
    }

    @Test
    void registrationWithValidatorErrorReturnsRegistrationPage() throws Exception {
        doAnswer(invocation -> {
            Errors errors = invocation.getArgument(1);
            errors.rejectValue("username", "username.exists", "Username already exists");
            return null;
        }).when(userValidator).validate(any(User.class), any(Errors.class));

        mockMvc.perform(post("/auth/registration")
                        .param("username", "existing-user")
                        .param("password", "password123")
                        .param("email", "existing-user@example.com")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("booksshop/auth/registration"));

        verify(userValidator).validate(any(User.class), any(Errors.class));
        verifyNoInteractions(registrationService);
    }
}

