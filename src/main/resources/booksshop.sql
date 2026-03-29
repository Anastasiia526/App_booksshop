CREATE DATABASE booksshop;

USE booksshop;

CREATE TABLE books_info(
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
product_code int NOT NULL UNIQUE,
isbn varchar(20) NOT NULL UNIQUE,
title varchar(200) NOT NULL,
brand varchar(50),
year_of_publication int,
language varchar(30),
price decimal(10, 2),
available TINYINT,
descriptions text
);

ALTER TABLE books_info ADD COLUMN image_path VARCHAR(255);

ALTER TABLE books_info DROP image_path;

ALTER TABLE books_info ADD COLUMN literary_genres_id BIGINT;

ALTER TABLE books_info 
ADD CONSTRAINT fk_literary_genres_id
FOREIGN KEY (literary_genres_id) REFERENCES literary_genres(id);

ALTER TABLE books_info 
DROP FOREIGN KEY fk_literary_genres_id;

DROP table books_info;

CREATE TABLE authors_books(
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
full_name varchar(150) NOT NULL
);

DROP table authors_books;

CREATE TABLE books_info_authors(
books_info_id BIGINT,
authors_books_id BIGINT,
PRIMARY KEY(books_info_id, authors_books_id),
FOREIGN KEY(books_info_id) REFERENCES books_info(id) ON DELETE CASCADE,
FOREIGN KEY(authors_books_id) REFERENCES authors_books(id) ON DELETE CASCADE
);

DROP table books_info_authors;

CREATE TABLE literary_genres(
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name varchar(50) NOT NULL
);

DROP table literary_genres;

CREATE TABLE book_genres(
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
literary_genres_id BIGINT,
name varchar(50) NOT NULL,
FOREIGN KEY(literary_genres_id) REFERENCES literary_genres(id)
);

DROP table book_genres;

CREATE TABLE categories_by_books(
book_genres_id BIGINT,
books_info_id BIGINT,
PRIMARY KEY(book_genres_id, books_info_id),
FOREIGN KEY(book_genres_id) REFERENCES book_genres(id),
FOREIGN KEY(books_info_id) REFERENCES books_info(id)
);

DROP table categories_by_books;


DROP table orders;
DROP table booksOrder;
DROP table books_order;

CREATE TABLE orders(
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
users_id Bigint,
last_name varchar(100) not null,
first_name varchar(100) not null,
email varchar(100),
phone varchar(30) not null,
delivery_City varchar(50),
delivery_Region varchar(50),
delivery_Method varchar(50),
delivery_Address varchar(150),
cc_number varchar(30),
cc_expiration varchar(50),
cc_cvv varchar(3),
FOREIGN KEY(users_id) REFERENCES users(id)
);

CREATE TABLE booksOrder(
orders_id BIGINT,
books_info_id BIGINT,
PRIMARY KEY(orders_id, books_info_id),
FOREIGN KEY(orders_id) REFERENCES orders(id),
FOREIGN KEY(books_info_id) REFERENCES books_info(id)
);

CREATE TABLE users(
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(100) NOT NULL,
roles VARCHAR(20),
password VARCHAR(255) NOT NULL,
email VARCHAR(100)
);
