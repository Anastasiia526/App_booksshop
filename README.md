# BookShop Application

## Description

BookShop is a backend web application for managing an online bookstore.
It allows users to browse books, manage orders, and interact with the system through a structured MVC architecture.

The project is built using **Spring Boot** and demonstrates core backend development skills, including working with databases, RESTful services, and layered architecture.
---

## Technologies

* Java
* Spring Boot
* Spring MVC
* Spring Data JPA (Hibernate)
* MySQL
* Maven
* Thymeleaf
---

##  Features

*  Book management (add, edit, delete, view books)
*  Order creation and management
*  Customer data handling
*  Integration with relational database (MySQL)
*  Layered architecture (Controller → Service → Repository)
---

## Architecture

The application follows a standard layered structure:

* **Controller** — handles HTTP requests and user interactions
* **Service** — contains business logic
* **Repository** — manages database operations using JPA/Hibernate
* **Model (Entity)** — represents database tables
---

##  How to run

1. Clone the repository:

```
git clone https://github.com/Anastasiia526/App_booksshop.git
```

2. Configure database in `application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/bookshop
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Build the project:

```
mvn clean install
```

4. Run the application:

```
mvn spring-boot:run
```

5. Open in browser:

```
http://localhost:8080
```

---

## Main Functionality

* Manage books catalog
* Create and process orders
* Work with customer data
* Server-side rendering using Thymeleaf

---

## Future Improvements

* Implement shopping cart functionality
* Improve order processing logic
* Implement REST API for frontend integration
* Add Swagger/OpenAPI documentation
* Dockerize the application

---
