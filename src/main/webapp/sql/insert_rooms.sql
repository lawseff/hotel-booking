USE hotel_booking;

DELETE FROM room;

INSERT INTO room (room_class_id, room_number, beds_amount)
	VALUES 
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), '135a', 2),
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), '136', 2),
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), '134', 3),
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), '88', 1),
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), '75', 1),
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), '205b', 4),
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), '207', 4),
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), '207', 6),
    ((SELECT id FROM room_class WHERE class_name='STANDARD'), '208', 6),
    
    ((SELECT id FROM room_class WHERE class_name='LUXE'), '11', 2),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), '13', 2),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), '15', 2),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), '304', 4),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), '306', 4),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), '308', 4),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), '105', 3),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), '405', 3),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), '35', 1),
    ((SELECT id FROM room_class WHERE class_name='LUXE'), '35', 6),
    
    ((SELECT id FROM room_class WHERE class_name='VIP'), '500', 1),
    ((SELECT id FROM room_class WHERE class_name='VIP'), '501a', 1),
    ((SELECT id FROM room_class WHERE class_name='VIP'), '502', 1),
    ((SELECT id FROM room_class WHERE class_name='VIP'), '510', 2),
    ((SELECT id FROM room_class WHERE class_name='VIP'), '512', 2),
    ((SELECT id FROM room_class WHERE class_name='VIP'), '514', 2),
    ((SELECT id FROM room_class WHERE class_name='VIP'), '535', 3),
    ((SELECT id FROM room_class WHERE class_name='VIP'), '537b', 3),
    ((SELECT id FROM room_class WHERE class_name='VIP'), '540', 4),
    ((SELECT id FROM room_class WHERE class_name='VIP'), '541', 4),
    ((SELECT id FROM room_class WHERE class_name='VIP'), '543', 4),
    ((SELECT id FROM room_class WHERE class_name='VIP'), '550', 6),
    ((SELECT id FROM room_class WHERE class_name='VIP'), '551', 6),
    ((SELECT id FROM room_class WHERE class_name='VIP'), '552', 6);
    