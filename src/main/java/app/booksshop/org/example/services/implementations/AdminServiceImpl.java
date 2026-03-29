package app.booksshop.org.example.services.implementations;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void doAdminStuff() {
        System.out.println("Only admin here");
    }
}
