package roomescape.mapper;

import roomescape.dto.RequestCreateTime;
import roomescape.dto.ResponseTime;
import roomescape.entity.Time;

public class TimeMapper {

    public static Time toEntity(RequestCreateTime requestCreateTime) {
        return Time.builder()
            .time(requestCreateTime.time())
            .build();
    }

    public static ResponseTime toResponse(Time time) {
        return new ResponseTime(
            time.getId(),
            time.getTime()
        );
    }
}
