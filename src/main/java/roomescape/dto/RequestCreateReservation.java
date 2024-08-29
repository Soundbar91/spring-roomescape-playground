package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import roomescape.domain.Reservation;

public record RequestCreateReservation(
        @NotBlank(message = "이름은 필수 입력사항 입니다.") String name,
        @NotBlank(message = "날짜는 필수 입력사항 입니다.") String date,
        @NotBlank(message = "시간은 필수 입력사항 입니다.") String time
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
