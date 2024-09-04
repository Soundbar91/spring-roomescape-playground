package roomescape.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;
import roomescape.dto.RequestCreateReservation;
import roomescape.dto.ResponseReservation;
import roomescape.mapper.ReservationMapper;

@Repository
public class ReservationDao {

    private final ReservationMapper reservationMapper;
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(ReservationMapper reservationMapper, JdbcTemplate jdbcTemplate) {
        this.reservationMapper = reservationMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    public ResponseReservation createReservation(RequestCreateReservation requestCreateReservation) {
        String sql = "INSERT INTO RESERVATION(name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setString(1, requestCreateReservation.name());
            ps.setString(2, requestCreateReservation.date().toString());
            ps.setString(3, requestCreateReservation.time().toString());
            return ps;
        }, keyHolder);

        return reservationMapper.toResponse(
            reservationMapper.toEntity(keyHolder.getKey().longValue(), requestCreateReservation)
        );
    }

    public List<ResponseReservation> getReservations() {
        String sql = "SELECT * FROM RESERVATION";
        return jdbcTemplate.query(sql,
            (r, s) -> reservationMapper.toResponse(new Reservation(
                r.getLong("id"),
                r.getString("name"),
                LocalDate.parse(r.getString("date")),
                LocalTime.parse(r.getString("time")))
            ));
    }

    public int deleteReservation(Long id) {
        String sql = "DELETE FROM RESERVATION WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

}
