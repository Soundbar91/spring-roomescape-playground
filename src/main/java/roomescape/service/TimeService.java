package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import roomescape.dao.TimeDao;
import roomescape.dto.RequestCreateTime;
import roomescape.dto.ResponseTime;
import roomescape.entity.Time;
import roomescape.mapper.TimeMapper;

@Service
public class TimeService {

    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public ResponseTime createTime(RequestCreateTime requestCreateTime) {
        Time time = timeDao.createTime(TimeMapper.toEntity(requestCreateTime));
        return TimeMapper.toResponse(time);
    }

    public List<ResponseTime> getTimes() {
        return timeDao.getTimes().stream()
            .map(TimeMapper::toResponse)
            .toList();
    }

    public void deleteTime(Long id) {
        Time time = timeDao.getTime(id);
        if (time == null) {
            throw new NoSuchElementException("존재하지 않는 시간 정보입니다.");
        }

        timeDao.deleteTime(id);
    }
}
