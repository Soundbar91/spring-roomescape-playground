package roomescape.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ResponseReservation(
    Long id,
    String name,

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate date,

    ResponseTime time
) {
}
