package run.al.runservice.business.service.interfaces;

import run.al.runservice.business.dto.RunDto;

import java.util.List;

public interface StravaRunService {

    List<RunDto> synchronizeStravaActivities(Long runnerId, String token);
}
