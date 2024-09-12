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

    private final ReservationMapper reservationMapper;
    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(ReservationMapper reservationMapper, ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationMapper = reservationMapper;
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    public ResponseReservation createReservation(RequestCreateReservation requestCreateReservation) {
        Time time = timeDao.getTimeById(requestCreateReservation.time());
        if (time == null) {
            throw new NoSuchElementException("예약 시간이 존재하지 않습니다.");
        }

        Reservation reservation = reservationDao.createReservation(
            reservationMapper.toEntity(requestCreateReservation, time));
        return reservationMapper.toResponse(reservation);
    }

    public List<ResponseReservation> getReservations() {
        return reservationDao.getReservations().stream()
            .map(reservationMapper::toResponse)
            .toList();
    }

    public void deleteReservation(Long id) {
        if (!reservationDao.deleteReservation(id)) {
            throw new NoSuchElementException("존재하지 않는 예약 정보입니다");
        }
    }
}
