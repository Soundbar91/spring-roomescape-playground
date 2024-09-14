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

import roomescape.dto.RequestCreateTime;
import roomescape.dto.ResponseTime;
import roomescape.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<ResponseTime> createTime(
        @RequestBody RequestCreateTime requestCreateTime
    ) {
        ResponseTime responseTime = timeService.createTime(requestCreateTime);
        return ResponseEntity
            .created(URI.create("/times/" + responseTime.id()))
            .body(responseTime);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTime>> getTimes() {
        List<ResponseTime> responseTimes = timeService.getTimes();
        return ResponseEntity.ok(responseTimes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(
        @PathVariable Long id
    ) {
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
