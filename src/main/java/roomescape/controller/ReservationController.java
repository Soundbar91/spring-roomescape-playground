package roomescape.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import roomescape.domain.Reservation;
import roomescape.dto.RequestCreateReservation;
import roomescape.dto.ResponseReservation;
import roomescape.mapper.ReservationMapper;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);
    private final ReservationMapper reservationMapper;
    private JdbcTemplate jdbcTemplate;

    public ReservationController(ReservationMapper reservationMapper, JdbcTemplate jdbcTemplate) {
        this.reservationMapper = reservationMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping
    public ResponseEntity<ResponseReservation> createReservation(
        @Valid @RequestBody RequestCreateReservation requestCreateReservation
    ) {
        Reservation reservation = reservationMapper.toEntity(index.getAndIncrement(), requestCreateReservation);
        reservations.add(reservation);

        return ResponseEntity
            .created(URI.create("/reservations/" + reservation.getId()))
            .body(reservationMapper.toResponse(reservation));
    }

    @GetMapping
    public ResponseEntity<List<ResponseReservation>> getReservations() {
        final String sql = "SELECT * FROM RESERVATION";
        List<ResponseReservation> responseReservations = jdbcTemplate.query(sql,
            (r, s) -> reservationMapper.toResponse(new Reservation(
                r.getLong("id"),
                r.getString("name"),
                LocalDate.parse(r.getString("date")),
                LocalTime.parse(r.getString("time")))
            ));

        return ResponseEntity.ok(responseReservations);
    }

    @DeleteMapping("/{id}")
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
