package com.booking.service.impl.reservation;

import com.booking.entity.User;
import com.booking.entity.reservation.Reservation;
import com.booking.profiling.time.Profiled;
import com.booking.repository.ReservationRepository;
import com.booking.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Primary
public class OldReservationServiceHelperImpl implements ReservationServiceHelper {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public OldReservationServiceHelperImpl(UserRepository userRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    @Profiled
    @Override
    @Transactional
    public List<Reservation> findAllReservations() {
        List<User> users = userRepository.findAll();
        List<Reservation> reservations = new ArrayList<>();
        for (User user : users) {
            reservations.addAll(reservationRepository.findByUserId(user.getId()));
        }
        return reservations;
    }

}
