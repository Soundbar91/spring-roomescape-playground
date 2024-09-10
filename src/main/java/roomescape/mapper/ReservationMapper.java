package roomescape.mapper;

import org.springframework.stereotype.Component;

import roomescape.entity.Reservation;
import roomescape.dto.RequestCreateReservation;
import roomescape.dto.ResponseReservation;

@Component
public class ReservationMapper {

    public Reservation toEntity(Long id, RequestCreateReservation requestCreateReservation) {
        if (requestCreateReservation == null) {
            return null;
        }

        return Reservation.builder()
            .id(id)
            .name(requestCreateReservation.name())
            .date(requestCreateReservation.date())
            .time(requestCreateReservation.time())
            .build();
    }

    public Reservation toEntity(RequestCreateReservation requestCreateReservation) {
        if (requestCreateReservation == null) {
            return null;
        }

        return Reservation.builder()
            .name(requestCreateReservation.name())
            .date(requestCreateReservation.date())
            .time(requestCreateReservation.time())
            .build();
    }

    public ResponseReservation toResponse(Long id, Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        return new ResponseReservation(
            id,
            reservation.getName(),
            reservation.getDate(),
            reservation.getTime()
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
