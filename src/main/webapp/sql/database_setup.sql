DROP DATABASE IF EXISTS hotel_booking;
CREATE DATABASE hotel_booking;
USE hotel_booking;

CREATE TABLE booking_user (
	id int NOT NULL AUTO_INCREMENT,
    is_admin bool NOT NULL DEFAULT false,
    email varchar(255) NOT NULL UNIQUE,
    user_password varchar(255) NOT NULL,
    first_name varchar(100) NOT NULL,
    second_name varchar(100) NOT NULL,
    
    PRIMARY KEY (id)
);

CREATE TABLE room_class (
	id int NOT NULL AUTO_INCREMENT,
    class_name varchar(100) NOT NULL UNIQUE,
    
    /* per night */
    basic_rate decimal(10,2) NOT NULL,
    rate_per_person decimal(10,2) NOT NULL,
    
    PRIMARY KEY (id)
);

CREATE TABLE room (
	id int NOT NULL AUTO_INCREMENT,
    is_active bool NOT NULL DEFAULT true,
    room_class_id int NOT NULL,
    beds_amount tinyint NOT NULL,
    
    PRIMARY KEY (id),
    FOREIGN KEY (room_class_id) REFERENCES room_class(id)
);

CREATE TABLE reservation (
	id int NOT NULL AUTO_INCREMENT,
    user_id int NOT NULL,
    room_class_id int NOT NULL,
    room_id int,
    reservation_status enum('WAITING', 'APPROVED', 'PAID', 'CHECKED_IN', 'CHECKED_OUT', 'CANCELLED') NOT NULL,
    arrival_date date NOT NULL,
    departure_date date NOT NULL,
    persons_amount tinyint NOT NULL,
    total_price decimal(10,2),
    
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES booking_user(id),
    FOREIGN KEY (room_class_id) REFERENCES room_class(id),
    FOREIGN KEY (room_id) REFERENCES room(id)
);