package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;
import roomescape.dto.RequestCreateReservation;
import roomescape.dto.ResponseReservation;
import roomescape.mapper.ReservationMapper;

@Repository
public class ReservationDao {

    private final ReservationMapper reservationMapper;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationDao(ReservationMapper reservationMapper, JdbcTemplate jdbcTemplate) {
        this.reservationMapper = reservationMapper;
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
    }

    public ResponseReservation createReservation(RequestCreateReservation requestCreateReservation) {
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("name", requestCreateReservation.name())
            .addValue("date", requestCreateReservation.date().toString())
            .addValue("time", requestCreateReservation.time().toString());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return reservationMapper.toResponse(
            reservationMapper.toEntity(id, requestCreateReservation)
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
