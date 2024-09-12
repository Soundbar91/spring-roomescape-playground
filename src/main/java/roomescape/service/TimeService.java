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

    private final TimeMapper timeMapper;
    private final TimeDao timeDao;

    public TimeService(TimeMapper timeMapper, TimeDao timeDao) {
        this.timeMapper = timeMapper;
        this.timeDao = timeDao;
    }

    public ResponseTime createTime(RequestCreateTime requestCreateTime) {
        Time time = timeDao.createTime(timeMapper.toEntity(requestCreateTime));
        return timeMapper.toResponse(time);
    }

    public List<ResponseTime> getTimes() {
        return timeDao.getTimes().stream()
            .map(timeMapper::toResponse)
            .toList();
    }

    public void deleteTime(Long id) {
        if (!timeDao.deleteTime(id)) {
            throw new NoSuchElementException("존재하지 않는 시간 정보입니다.");
        }
    }
}
