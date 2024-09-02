package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ResponseReservation(
    Long id,
    String name,

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate date,

    @JsonFormat(pattern = "HH:mm")
    LocalTime time
) {
}
