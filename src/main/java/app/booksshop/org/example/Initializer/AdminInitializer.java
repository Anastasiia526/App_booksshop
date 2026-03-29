package app.booksshop.org.example.Initializer;


import app.booksshop.org.example.entities.User;
import app.booksshop.org.example.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminInitializer(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    public void createAdmin(){
        if(userRepository.findByUsername("admin").isEmpty()){
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(bCryptPasswordEncoder.encode("admin"));
            admin.setRole("ROLE_ADMIN");

            userRepository.save(admin);
        }
    }

}
