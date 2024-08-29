package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.RequestCreateReservation;
import roomescape.domain.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(
            @Valid @RequestBody RequestCreateReservation requestCreateReservation
    ) {
        Reservation reservation = requestCreateReservation.toReservation(index.getAndIncrement());

        reservations.add(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId())).body(reservation);
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservations;
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id
    ) throws IllegalAccessException {
        Reservation reservation = reservations.stream()
                .filter(r -> Objects.equals(r.getId(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalAccessException("존재하지 않는 정보입니다"));

        reservations.remove(reservation);
        return ResponseEntity.noContent().build();
    }
}
