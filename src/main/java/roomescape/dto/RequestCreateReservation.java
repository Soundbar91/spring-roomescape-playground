package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record RequestCreateReservation(
        @NotBlank(message = "이름은 필수 입력사항 입니다.")
        String name,

        @NotNull(message = "날짜는 필수 입력사항 입니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotNull(message = "시간은 필수 입력사항 입니다.")
        @DateTimeFormat(pattern = "HH-mm")
        LocalTime time
) {
    public Reservation toReservation(Long id) {
        return new Reservation(
                id,
                this.name,
                this.date,
                this.time
        );
    }
}
