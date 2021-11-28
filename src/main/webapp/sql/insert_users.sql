USE hotel_booking;

DELETE FROM booking_user;

INSERT INTO booking_user (is_admin, email, user_password, first_name, second_name)
	VALUES (true, 'admin@gmail.com', md5('admin'), 'admin', 'admin');

INSERT INTO booking_user (email, user_password, first_name, second_name)
VALUES ('user@gmail.com', md5('12345'), 'John', 'Doe');

