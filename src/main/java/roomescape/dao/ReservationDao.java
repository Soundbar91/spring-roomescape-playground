package roomescape.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.entity.Reservation;
import roomescape.entity.Time;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("reservation")
            .usingColumns("name", "date", "time_id")
            .usingGeneratedKeyColumns("id");
    }

    public Reservation createReservation(Reservation reservation) {
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("name", reservation.getName())
            .addValue("date", reservation.getDate())
            .addValue("time_id", reservation.getTime().getId());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Reservation.builder()
            .id(id)
            .name(reservation.getName())
            .date(reservation.getDate())
            .time(reservation.getTime())
            .build();
    }

    public List<Reservation> getReservations() {
        String sql = """
                SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.time as time_value
            FROM reservation as r inner join time as t on r.time_id = t.id
            """;

        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public boolean deleteReservation(Long id) {
        String sql = "DELETE FROM RESERVATION WHERE id = ?";

        return jdbcTemplate.update(sql, id) != 0;
    }

    private final RowMapper<Reservation> reservationRowMapper = (r, rowNum) -> new Reservation(
        r.getLong("id"),
        r.getString("name"),
        r.getDate("date").toLocalDate(),
        Time.builder().time(r.getTime("time").toLocalTime()).build()
    );
}
