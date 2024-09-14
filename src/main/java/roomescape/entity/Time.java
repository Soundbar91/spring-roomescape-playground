package roomescape.entity;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class Time {
    private Long id;
    private LocalTime time;

    @Builder(toBuilder = true)
    public Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }
}
