package run.al.runservice.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import run.al.runservice.business.dto.RunDto;
import run.al.runservice.business.dto.SearchRunBetweenDatesDto;
import run.al.runservice.business.service.interfaces.RunService;

import java.util.List;

@RestController
@RequestMapping("/run")
@RequiredArgsConstructor
public class RunController {

    private final RunService runService;

    @PostMapping("/save/manual")
    public ResponseEntity<RunDto> saveRunManually(@RequestBody RunDto runDto, @RequestHeader("loggedInUser") Long runnerId) {
        return ResponseEntity.ok(runService.saveRun(runnerId, runDto));
    }

    @PostMapping("/between/dates")
    public ResponseEntity<List<RunDto>> getRunsBetweenDates(@RequestBody SearchRunBetweenDatesDto searchRunBetweenDatesDto) {
        return ResponseEntity.ok(runService.getAllRunsOfARunnerBetweenDates(searchRunBetweenDatesDto));
    }

}
