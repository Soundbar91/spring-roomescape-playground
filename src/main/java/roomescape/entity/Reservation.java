package roomescape.entity;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private Time time;

    @Builder
    public Reservation(Long id, String name, LocalDate date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
