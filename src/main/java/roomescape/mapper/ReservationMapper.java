package roomescape.mapper;

import org.springframework.stereotype.Component;

import roomescape.dto.RequestCreateReservation;
import roomescape.dto.ResponseReservation;
import roomescape.entity.Reservation;
import roomescape.entity.Time;

@Component
public class ReservationMapper {

    public Reservation toEntity(RequestCreateReservation requestCreateReservation, Time time) {
        return Reservation.builder()
            .name(requestCreateReservation.name())
            .date(requestCreateReservation.date())
            .time(time)
            .build();
    }

    public ResponseReservation toResponse(Reservation reservation) {
        return new ResponseReservation(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate(),
            reservation.getTime()
        );
    }
}
