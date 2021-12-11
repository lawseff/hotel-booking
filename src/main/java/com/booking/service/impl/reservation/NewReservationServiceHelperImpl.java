package com.booking.service.impl.reservation;

import com.booking.entity.reservation.Reservation;
import com.booking.profiling.time.Profiled;
import com.booking.repository.ReservationRepository;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class NewReservationServiceHelperImpl implements ReservationServiceHelper {

    private final ReservationRepository repository;

    public NewReservationServiceHelperImpl(ReservationRepository repository) {
        this.repository = repository;
    }

    @Profiled
    @Override
    public List<Reservation> findAllReservations() {
        return repository.findAll();
    }

}
