package run.al.runservice.business.service.interfaces;

import run.al.runservice.business.dto.RunDto;
import run.al.runservice.business.dto.SearchRunBetweenDatesDto;

import java.util.List;

public interface RunService {

    RunDto saveRun(Long runnerId, RunDto runDto);

    List<RunDto> getAllRunsOfARunnerBetweenDates(SearchRunBetweenDatesDto searchRunBetweenDatesDto);
}
