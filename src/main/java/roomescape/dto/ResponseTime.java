package roomescape.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ResponseTime(
    Long id,

    @JsonFormat(pattern = "HH:mm")
    LocalTime time
) {
}
