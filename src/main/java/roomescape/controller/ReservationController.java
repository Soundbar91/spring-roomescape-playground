package roomescape.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import roomescape.dto.RequestCreateReservation;
import roomescape.domain.Reservation;
import roomescape.dto.ResponseReservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @PostMapping("/reservations")
    public ResponseEntity<ResponseReservation> createReservation(
        @Valid @RequestBody RequestCreateReservation requestCreateReservation
    ) {
        Reservation reservation = requestCreateReservation.toReservation(index.getAndIncrement());
        reservations.add(reservation);

        return ResponseEntity
            .created(URI.create("/reservations/" + reservation.getId()))
            .body(ResponseReservation.of(reservation));
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ResponseReservation>> getReservations() {
        List<ResponseReservation> reservationList = reservations.stream().map(ResponseReservation::of).toList();
        return ResponseEntity.ok(reservationList);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(
        @PathVariable Long id
    ) {
        Reservation reservation = reservations.stream()
            .filter(r -> Objects.equals(r.getId(), id))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 예약 정보입니다"));

        reservations.remove(reservation);
        return ResponseEntity.noContent().build();
    }
}
