package roomescape.entity;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    @Builder
    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
