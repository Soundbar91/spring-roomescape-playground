package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ResponseReservation(
        Long id,
        String name,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @JsonFormat(pattern = "HH-mm")
        LocalTime time
) {
    public static ResponseReservation of(Reservation reservation) {
        return new ResponseReservation(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
