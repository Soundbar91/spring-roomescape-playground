package roomescape.mapper;

import org.springframework.stereotype.Component;

import roomescape.domain.Reservation;
import roomescape.dto.RequestCreateReservation;
import roomescape.dto.ResponseReservation;

@Component
public class ReservationMapper {

    public Reservation toEntity(Long id, RequestCreateReservation requestCreateReservation) {
        if (requestCreateReservation == null) {
            return null;
        }

        return new Reservation(
            id,
            requestCreateReservation.name(),
            requestCreateReservation.date(),
            requestCreateReservation.time()
        );
    }

    public ResponseReservation toResponse(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        return new ResponseReservation(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate(),
            reservation.getTime()
        );
    }
}
