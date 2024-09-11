package roomescape.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.dto.RequestCreateTime;
import roomescape.dto.ResponseTime;
import roomescape.entity.Time;
import roomescape.mapper.TimeMapper;

@Repository
public class TimeDao {

    private final TimeMapper timeMapper;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public TimeDao(TimeMapper timeMapper, DataSource dataSource) {
        this.timeMapper = timeMapper;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("time")
            .usingColumns("time")
            .usingGeneratedKeyColumns("id");
    }

    public ResponseTime createTime(RequestCreateTime requestCreateTime) {
        Time time = timeMapper.toEntity(requestCreateTime);

        SqlParameterSource params = new BeanPropertySqlParameterSource(time);
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return timeMapper.toResponse(id, time);
    }

    public List<ResponseTime> getTimes() {
        String sql = "SELECT * FROM TIME";

        return jdbcTemplate.query(sql, timeRowMapper).stream()
            .map(timeMapper::toResponse)
            .toList();
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
