package run.al.runservice.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.al.runservice.business.dto.RunDto;
import run.al.runservice.business.service.interfaces.RunService;
import run.al.runservice.persistence.model.Run;
import run.al.runservice.persistence.repository.RunRepository;

@Service
@RequiredArgsConstructor
public class RunServiceImpl implements RunService {

    private final RunRepository runRepository;

    @Override
    @Transactional
    public RunDto saveRun(Long runnerId, RunDto runDto) {
        Run runToSave = new Run();
        runToSave.setRunnerId(runnerId);
        RunDto runDtoToReturn = new RunDto();
        BeanUtils.copyProperties(runDto, runToSave);
        Run savedRun = this.runRepository.save(runToSave);
        BeanUtils.copyProperties(savedRun, runDtoToReturn);
        return runDtoToReturn;
    }
}
