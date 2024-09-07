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
import roomescape.dto.RequestCreateReservation;
import roomescape.dto.ResponseReservation;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @PostMapping
    public ResponseEntity<ResponseReservation> createReservation(
        @Valid @RequestBody RequestCreateReservation requestCreateReservation
    ) {
        ResponseReservation responseReservation = reservationDao.createReservation(requestCreateReservation);

        return ResponseEntity
            .created(URI.create("/reservations/" + responseReservation.id()))
            .body(responseReservation);
    }

    @GetMapping
    public ResponseEntity<List<ResponseReservation>> getReservations() {
        List<ResponseReservation> responseReservations = reservationDao.getReservations();
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
