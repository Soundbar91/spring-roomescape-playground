package roomescape.mapper;

import org.springframework.stereotype.Component;

import roomescape.dto.RequestCreateTime;
import roomescape.dto.ResponseTime;
import roomescape.entity.Time;

@Component
public class TimeMapper {

    public Time toEntity(RequestCreateTime requestCreateTime) {
        if (requestCreateTime == null) {
            return null;
        }

        return Time.builder()
            .time(requestCreateTime.time())
            .build();
    }

    public Time toEntity(Long id, RequestCreateTime requestCreateTime) {
        if (requestCreateTime == null) {
            return null;
        }

        return Time.builder()
            .id(id)
            .time(requestCreateTime.time())
            .build();
    }

    public ResponseTime toResponse(Time time) {
        if (time == null) {
            return null;
        }

        return new ResponseTime(
            time.getId(),
            time.getTime()
        );
    }

    public ResponseTime toResponse(Long id, Time time) {
        if (time == null) {
            return null;
        }

        return new ResponseTime(
            id,
            time.getTime()
        );
    }
}
