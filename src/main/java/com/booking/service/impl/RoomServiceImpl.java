package com.booking.service.impl;

import com.booking.validation.api.PriceValidator;
import com.booking.exception.ServiceException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import com.booking.entity.room.RoomClass;
import com.booking.repository.RoomClassRepository;
import com.booking.repository.RoomRepository;
import com.booking.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

    private static final String ROOM_CLASSES_ATTRIBUTE = "room_classes";
    private static final String ROOMS_ATTRIBUTE = "rooms";

    private final RoomRepository roomRepository;
    private final RoomClassRepository roomClassRepository;
    private final PriceValidator priceValidator;

    public RoomServiceImpl(RoomRepository roomRepository, RoomClassRepository roomClassRepository, PriceValidator priceValidator) {
        this.roomRepository = roomRepository;
        this.roomClassRepository = roomClassRepository;
        this.priceValidator = priceValidator;
    }

    @Override
    public void setRooms(Model model) {
        model.addAttribute(ROOM_CLASSES_ATTRIBUTE, roomClassRepository.findAll());
        model.addAttribute(ROOMS_ATTRIBUTE, roomRepository.findAll());
    }

    @Override
    @Transactional
    public void changeRoomStatus(Integer id, boolean active) {
        roomRepository.findById(id)
                .ifPresent(room -> room.setActive(active));
    }

    @Override
    @Transactional
    public void savePrices(List<RoomClass> roomClasses) throws ServiceException {
        for (RoomClass roomClass : roomClasses) {

            if (!priceValidator.isPriceValid(roomClass.getBasicRate())
                    && !priceValidator.isPriceValid(roomClass.getRatePerPerson())) {
                throw new ServiceException("Bad request");
            }

            Optional<RoomClass> optional = roomClassRepository.findByName(roomClass.getName());
            if (optional.isPresent()) {
                RoomClass persisted = optional.get();
                persisted.setBasicRate(roomClass.getBasicRate());
                persisted.setRatePerPerson(roomClass.getRatePerPerson());
            } else {
                throw new ServiceException("Room class not found: " + roomClass.getName());
            }
        }
    }

}