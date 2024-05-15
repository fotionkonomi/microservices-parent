package run.al.runservice.business.service.interfaces;

import run.al.runservice.business.dto.RunDto;

public interface RunService {

    RunDto saveRun(Long runnerId, RunDto runDto);
}
