package com.booking.profiling.test;

import com.booking.entity.User;
import com.booking.entity.reservation.Reservation;
import com.booking.entity.reservation.ReservationStatus;
import com.booking.entity.room.Room;
import com.booking.repository.ReservationRepository;
import com.booking.repository.RoomRepository;
import com.booking.repository.UserRepository;
import com.booking.utils.ReservationPriceCalculator;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.github.javafaker.service.RandomService;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "test.data.generation", name = "enabled", havingValue = "true")
@Slf4j
public class TestDataGenerator implements ApplicationListener<ApplicationStartedEvent> {

    private static final String PASSWORD = DigestUtils.md5Hex("12345");

    @Value("${test.data.generation.users}")
    private int userCount;

    @Value("${test.data.generation.reservations.per.user}")
    private int reservationsPerUser;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RoomRepository roomRepository;

    private Faker faker = new Faker();
    private RandomService random = faker.random();

    private List<Room> rooms;

    @PostConstruct
    private void init() {
        rooms = roomRepository.findAll();
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        for (int i = 0; i < userCount; i++) {
            try {
                User user = userRepository.save(generateUser());
                for (int j = 0; j < reservationsPerUser; j++) {
                    Reservation reservation = generateReservation(user);
                    reservationRepository.save(reservation);
                }
            } catch (RuntimeException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private User generateUser() {
        User user = new User();
        Name name = faker.name();
        user.setFirstName(name.firstName());
        user.setSecondName(name.lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(PASSWORD);
        return user;
    }

    private Reservation generateReservation(User user) {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        Room room = randomItem(rooms);
        reservation.setRoom(room);
        reservation.setPersonsAmount(random.nextInt(1, room.getBedsAmount()));

        ZonedDateTime arrivalDate = ZonedDateTime.now().plusDays(random.nextInt(1, 200));
        ZonedDateTime departureDate = arrivalDate.plusDays(random.nextInt(1, 14));

        reservation.setArrivalDate(Date.from(arrivalDate.toInstant()));
        reservation.setDepartureDate(Date.from(departureDate.toInstant()));
        reservation.setRoomClass(room.getRoomClass());
        reservation.setReservationStatus(randomItem(Arrays.asList(ReservationStatus.values())));

        reservation.setTotalPrice(ReservationPriceCalculator.calculateReservationPrice(reservation));

        return reservation;
    }

    private <T> T randomItem(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }


}
