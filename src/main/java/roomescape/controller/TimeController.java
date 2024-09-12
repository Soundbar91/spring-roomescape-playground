package roomescape.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import roomescape.dao.TimeDao;
import roomescape.dto.RequestCreateTime;
import roomescape.dto.ResponseTime;
import roomescape.entity.Time;
import roomescape.mapper.TimeMapper;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeMapper timeMapper;
    private final TimeDao timeDao;

    public TimeController(TimeMapper timeMapper, TimeDao timeDao) {
        this.timeMapper = timeMapper;
        this.timeDao = timeDao;
    }

    @PostMapping
    public ResponseEntity<ResponseTime> createTime(
        @RequestBody RequestCreateTime requestCreateTime
    ) {
        Time time = timeDao.createTime(timeMapper.toEntity(requestCreateTime));
        ResponseTime responseTime = timeMapper.toResponse(time);

        return ResponseEntity
            .created(URI.create("/times/" + responseTime.id()))
            .body(responseTime);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTime>> getTimes() {
        List<ResponseTime> responseTimes = timeDao.getTimes().stream()
            .map(timeMapper::toResponse)
            .toList();

        return ResponseEntity.ok(responseTimes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(
        @PathVariable Long id
    ) {
        timeDao.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
