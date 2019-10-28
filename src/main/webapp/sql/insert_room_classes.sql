USE hotel_booking;

DELETE FROM room_class;

INSERT INTO room_class (class_name, basic_rate, rate_per_person)
	VALUES ('STANDARD', 80, 15), ('LUXE', 120, 20), ('VIP', 145, 25);