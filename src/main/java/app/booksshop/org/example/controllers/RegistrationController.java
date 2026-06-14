package app.booksshop.org.example.controllers;

import app.booksshop.org.example.entities.User;
import app.booksshop.org.example.services.implementations.RegistrationServiceImpl;
import app.booksshop.org.example.validator.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/auth")
public class RegistrationController {

    private final RegistrationServiceImpl registrationService;
    private final UserValidator userValidator;

    @Autowired
    public RegistrationController(RegistrationServiceImpl registrationService, UserValidator userValidator) {
        this.registrationService = registrationService;
        this.userValidator = userValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "booksshop/auth/login";
    }


    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "booksshop/auth/registration";
    }


    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "booksshop/auth/registration";

        registrationService.register(user);

        return "redirect:/auth/login";
    }
}
