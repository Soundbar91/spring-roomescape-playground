package roomescape;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        Reservation reservation1 = new Reservation(index.getAndIncrement(), "브라움");
        reservations.add(reservation1);

        Reservation reservation2 = new Reservation(index.getAndIncrement(), "관규");
        reservations.add(reservation2);

        return reservations;
    }

}
