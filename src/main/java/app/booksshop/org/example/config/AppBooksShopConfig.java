package app.booksshop.org.example.config;

import app.booksshop.org.example.services.implementations.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableMethodSecurity
public class AppBooksShopConfig {

    private final UserServiceImpl userService;


    @Autowired
    public AppBooksShopConfig(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(auth -> auth
                        // тільки для адміна
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // публічні сторінки (включаючи весь магазин)
                        .requestMatchers("/auth/**",
                                "/booksshop/**",
                                "/images/**",
                                "/css/**",
                                "/js/**").permitAll()

                        // оформлення замовлення — тільки для авторизованих
                        .requestMatchers("/orders/**").hasAnyRole("USER", "ADMIN")

                        // все інше
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/orders", true)
                        .failureUrl("/auth/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login")
                );

        return http.build();
    }


}
