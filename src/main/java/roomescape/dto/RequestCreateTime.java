package roomescape.dto;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;

public record RequestCreateTime(
    @NotNull(message = "시간은 필수 입력사항 입니다.")
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime time
) {
}
