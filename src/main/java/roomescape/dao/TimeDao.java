package roomescape.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.entity.Time;

@Repository
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public TimeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("time")
            .usingColumns("time")
            .usingGeneratedKeyColumns("id");
    }

    public Time createTime(Time time) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(time);
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return time.toBuilder()
            .id(id)
            .build();
    }

    public Time getTime(Long id) {
        String sql = "SELECT * FROM TIME WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, timeRowMapper, id);
    }

    public List<Time> getTimes() {
        String sql = "SELECT * FROM TIME";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public void deleteTime(Long id) {
        String sql = "DELETE FROM TIME WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private final RowMapper<Time> timeRowMapper = (rs, rowNum) -> new Time(
        rs.getLong("id"),
        rs.getTime("time").toLocalTime()
    );
}
