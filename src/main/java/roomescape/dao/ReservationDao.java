package roomescape.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.dto.RequestCreateReservation;
import roomescape.dto.ResponseReservation;
import roomescape.entity.Reservation;
import roomescape.mapper.ReservationMapper;

@Repository
public class ReservationDao {

    private final ReservationMapper reservationMapper;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationDao(ReservationMapper reservationMapper, DataSource dataSource) {
        this.reservationMapper = reservationMapper;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("reservation")
            .usingColumns("name", "date", "time")
            .usingGeneratedKeyColumns("id");
    }

    public ResponseReservation createReservation(RequestCreateReservation requestCreateReservation) {
        Reservation reservation = reservationMapper.toEntity(requestCreateReservation);

        SqlParameterSource params = new BeanPropertySqlParameterSource(reservation);
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return reservationMapper.toResponse(id, reservation);
    }

    public List<ResponseReservation> getReservations() {
        String sql = "SELECT * FROM RESERVATION";

        return jdbcTemplate.query(sql, reservationRowMapper).stream()
            .map(reservationMapper::toResponse)
            .toList();
    }

    public boolean deleteReservation(Long id) {
        String sql = "DELETE FROM RESERVATION WHERE id = ?";

        return jdbcTemplate.update(sql, id) != 0;
    }

    private final RowMapper<Reservation> reservationRowMapper = (r, rowNum) -> new Reservation(
        r.getLong("id"),
        r.getString("name"),
        r.getDate("date").toLocalDate(),
        r.getTime("time").toLocalTime()
    );
}
