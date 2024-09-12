package roomescape.controller;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.dto.RequestCreateReservation;
import roomescape.dto.ResponseReservation;
import roomescape.entity.Reservation;
import roomescape.entity.Time;
import roomescape.mapper.ReservationMapper;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationMapper reservationMapper;
    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationController(ReservationMapper reservationMapper, ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationMapper = reservationMapper;
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    @PostMapping
    public ResponseEntity<ResponseReservation> createReservation(
        @Valid @RequestBody RequestCreateReservation requestCreateReservation
    ) {
        Time time = timeDao.getTimeById(requestCreateReservation.time());
        if (time == null) {
            throw new NoSuchElementException("예약 시간이 존재하지 않습니다.");
        }

        Reservation reservation = reservationDao.createReservation(
            reservationMapper.toEntity(requestCreateReservation, time));
        ResponseReservation responseReservation = reservationMapper.toResponse(reservation);

        return ResponseEntity
            .created(URI.create("/reservations/" + responseReservation.id()))
            .body(responseReservation);
    }

    @GetMapping
    public ResponseEntity<List<ResponseReservation>> getReservations() {
        List<ResponseReservation> responseReservations = reservationDao.getReservations().stream()
            .map(reservationMapper::toResponse)
            .toList();

        return ResponseEntity.ok(responseReservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
        @PathVariable Long id
    ) {
        if (!reservationDao.deleteReservation(id)) {
            throw new NoSuchElementException("존재하지 않는 예약 정보입니다");
        }

        return ResponseEntity.noContent().build();
    }
}
