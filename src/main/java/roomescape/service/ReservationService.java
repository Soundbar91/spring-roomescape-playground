package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.dto.RequestCreateReservation;
import roomescape.dto.ResponseReservation;
import roomescape.entity.Reservation;
import roomescape.entity.Time;
import roomescape.mapper.ReservationMapper;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    public ResponseReservation createReservation(RequestCreateReservation requestCreateReservation) {
        Time time = timeDao.getTime(requestCreateReservation.time());
        if (time == null) {
            throw new NoSuchElementException("예약 시간이 존재하지 않습니다.");
        }

        Reservation reservation = reservationDao.createReservation(
            ReservationMapper.toEntity(requestCreateReservation, time));

        return ReservationMapper.toResponse(reservation);
    }

    public List<ResponseReservation> getReservations() {
        return reservationDao.getReservations().stream()
            .map(ReservationMapper::toResponse)
            .toList();
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationDao.getReservation(id);
        if (reservation == null) {
            throw new NoSuchElementException("존재하지 않는 예약 정보입니다");
        }

        reservationDao.deleteReservation(id);
    }
}
