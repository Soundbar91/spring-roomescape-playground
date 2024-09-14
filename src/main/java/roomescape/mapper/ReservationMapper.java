package roomescape.mapper;

import roomescape.dto.RequestCreateReservation;
import roomescape.dto.ResponseReservation;
import roomescape.entity.Reservation;
import roomescape.entity.Time;

public class ReservationMapper {

    public static Reservation toEntity(RequestCreateReservation requestCreateReservation, Time time) {
        return Reservation.builder()
            .name(requestCreateReservation.name())
            .date(requestCreateReservation.date())
            .time(time)
            .build();
    }

    public static ResponseReservation toResponse(Reservation reservation) {
        return new ResponseReservation(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate(),
            TimeMapper.toResponse(reservation.getTime())
        );
    }
}
