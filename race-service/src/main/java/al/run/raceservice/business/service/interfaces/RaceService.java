package al.run.raceservice.business.service.interfaces;

import al.run.raceservice.business.dto.RaceDto;

public interface RaceService {

    RaceDto save(RaceDto raceDto);

    RaceDto getById(Long id);
}
