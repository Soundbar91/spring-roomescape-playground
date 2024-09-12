package roomescape.mapper;

import org.springframework.stereotype.Component;

import roomescape.dto.RequestCreateTime;
import roomescape.dto.ResponseTime;
import roomescape.entity.Time;

@Component
public class TimeMapper {

    public Time toEntity(RequestCreateTime requestCreateTime) {
        return Time.builder()
            .time(requestCreateTime.time())
            .build();
    }

    public ResponseTime toResponse(Time time) {
        return new ResponseTime(
            time.getId(),
            time.getTime()
        );
    }
}
