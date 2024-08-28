package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate data;
    private LocalTime time;

    public Reservation() {
    }

    public Reservation(Long id, String name) {
        this.id = id;
        this.name = name;
        data = LocalDate.now();
        time = LocalTime.now();
    }

    public String getName() {
        return name;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getTime() {
        return time;
    }

    public Long getId() {
        return id;
    }
}
