package run.al.runservice.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.al.runservice.business.dto.RunDto;
import run.al.runservice.business.dto.SearchRunBetweenDatesDto;
import run.al.runservice.business.events.RunSavedEvent;
import run.al.runservice.business.service.interfaces.RunService;
import run.al.runservice.persistence.model.Run;
import run.al.runservice.persistence.repository.RunRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RunServiceImpl implements RunService {

    private final RunRepository runRepository;
    private final KafkaTemplate<String, RunSavedEvent> kafkaTemplate;

    @Override
    @Transactional
    public RunDto saveRun(Long runnerId, RunDto runDto) {
        Run runToSave = new Run();
        BeanUtils.copyProperties(runDto, runToSave);
        runToSave.setRunnerId(runnerId);
        Run savedRun = this.runRepository.save(runToSave);
        RunDto createdRun = convertFromEntityToDto(savedRun);

        kafkaTemplate.send("runAddedTopic", new RunSavedEvent(createdRun));

        return createdRun;
    }

    @Override
    public List<RunDto> getAllRunsOfARunnerBetweenDates(SearchRunBetweenDatesDto searchRunBetweenDatesDto) {
        return runRepository.findAllByRunnerIdAndStartDateBetween(searchRunBetweenDatesDto.getRunnerId(), searchRunBetweenDatesDto.getDateFrom(), searchRunBetweenDatesDto.getDateUntil())
                .stream().map(this::convertFromEntityToDto).toList();
    }

    private RunDto convertFromEntityToDto(Run run) {
        RunDto runDtoToReturn = new RunDto();
        BeanUtils.copyProperties(run, runDtoToReturn);
        return runDtoToReturn;
    }
}
