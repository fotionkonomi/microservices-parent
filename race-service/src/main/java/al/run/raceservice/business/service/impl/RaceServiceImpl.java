package al.run.raceservice.business.service.impl;

import al.run.raceservice.business.dto.RaceDto;
import al.run.raceservice.business.exceptions.RaceNotFoundException;
import al.run.raceservice.business.service.interfaces.RaceService;
import al.run.raceservice.persistence.model.Race;
import al.run.raceservice.persistence.repository.RaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RaceServiceImpl implements RaceService {

    private final RaceRepository repository;

    @Override
    public RaceDto save(RaceDto raceDto) {
        Race raceToSave = new Race();

        BeanUtils.copyProperties(raceDto, raceToSave);
        Race savedRace = repository.save(raceToSave);

        RaceDto raceToReturn = new RaceDto();
        BeanUtils.copyProperties(savedRace, raceToReturn);

        return raceToReturn;
    }

    @Override
    public RaceDto getById(Long id) {
        Race race = repository.findById(id).orElseThrow(() -> new RaceNotFoundException(id));
        RaceDto raceDto = new RaceDto();
        BeanUtils.copyProperties(race, raceDto);
        return raceDto;
    }


}
