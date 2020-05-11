USE hotel_booking;

DELETE FROM booking_user;

INSERT INTO booking_user (is_admin, email, user_password, first_name, second_name)
	VALUES (true, 'admin', md5('admin'), 'admin', 'admin');

INSERT INTO booking_user (email, user_password, first_name, second_name) 
	VALUES ('test@foo.bar', md5('1234'), 'John', 'Doe'),
		('lorem@ipsum.dolor', md5('sir'), 'Amet', 'Consectetur'),
        ('ivanov@mail.ru', md5('1234'), 'Иван', 'Иванов');