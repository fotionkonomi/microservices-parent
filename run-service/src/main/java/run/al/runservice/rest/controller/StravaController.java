package run.al.runservice.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import run.al.runservice.business.dto.RunDto;
import run.al.runservice.business.service.interfaces.StravaRunService;
import run.al.runservice.rest.dto.StravaActivitiesRequestDto;

import java.util.List;

@RestController
@RequestMapping("/strava")
@RequiredArgsConstructor
public class StravaController {

    private final StravaRunService stravaRunService;

    @PostMapping("/sync")
    public ResponseEntity<List<RunDto>> synchronizeActivities(@RequestBody StravaActivitiesRequestDto stravaActivitiesRequestDto, @RequestHeader("loggedInUser") Long runnerId) {
        return ResponseEntity.ok(stravaRunService.synchronizeStravaActivities(runnerId, stravaActivitiesRequestDto.getToken()));
    }
}
