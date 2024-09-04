package roomescape.controller;

import java.net.URI;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO RESERVATION(name, date, time) VALUES (?, ?, ?)",
                new String[]{"id"});
            ps.setString(1, requestCreateReservation.name());
            ps.setString(2, requestCreateReservation.date().toString());
            ps.setString(3, requestCreateReservation.time().toString());

            return ps;
        }, keyHolder);

        Reservation reservation = reservationMapper.toEntity(keyHolder.getKey().longValue(), requestCreateReservation);
        ResponseReservation responseReservation = reservationMapper.toResponse(reservation);

        return ResponseEntity
            .created(URI.create("/reservations/" + keyHolder.getKey().longValue()))
            .body(responseReservation);
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
        final String sql = "DELETE FROM RESERVATION WHERE id = ?";
        if (jdbcTemplate.update(sql, id) == 0) {
            throw new NoSuchElementException("존재하지 않는 예약 정보입니다");
        }

        return ResponseEntity.noContent().build();
    }
}
