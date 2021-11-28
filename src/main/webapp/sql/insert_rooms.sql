USE hotel_booking;

DELETE FROM room;

INSERT INTO room (room_class_id, beds_amount)
	VALUES 
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), 2),
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), 2),
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), 3),
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), 1),
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), 1),
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), 4),
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), 4),
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), 6),
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), 6),
    
    ((SELECT id FROM room_class WHERE class_name='LUXE'), 2),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), 2),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), 2),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), 4),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), 4),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), 4),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), 3),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), 3),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), 1),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), 6),
    
    ((SELECT id FROM room_class WHERE class_name='VIP'), 1),
    ((SELECT id FROM room_class WHERE class_name='VIP'), 1),
    ((SELECT id FROM room_class WHERE class_name='VIP'), 1),
    ((SELECT id FROM room_class WHERE class_name='VIP'), 2),
    ((SELECT id FROM room_class WHERE class_name='VIP'), 2),
    ((SELECT id FROM room_class WHERE class_name='VIP'), 2),
    ((SELECT id FROM room_class WHERE class_name='VIP'), 3),
    ((SELECT id FROM room_class WHERE class_name='VIP'), 3),
    ((SELECT id FROM room_class WHERE class_name='VIP'), 4),
    ((SELECT id FROM room_class WHERE class_name='VIP'), 4),
    ((SELECT id FROM room_class WHERE class_name='VIP'), 4),
    ((SELECT id FROM room_class WHERE class_name='VIP'), 6),
    ((SELECT id FROM room_class WHERE class_name='VIP'), 6),
    ((SELECT id FROM room_class WHERE class_name='VIP'), 6);
    