package al.run.raceservice.rest.controller;

import al.run.raceservice.business.dto.RaceDto;
import al.run.raceservice.business.service.interfaces.RaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/race")
@RequiredArgsConstructor
public class RaceController {
    private final RaceService raceService;

    @PostMapping("/save")
    public ResponseEntity<RaceDto> saveRace(@RequestBody RaceDto raceDto) {
        return ResponseEntity.ok(raceService.save(raceDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RaceDto> getRaceById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(raceService.getById(id));
    }
}
