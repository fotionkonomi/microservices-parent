package run.al.runservice.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import run.al.runservice.business.dto.RunDto;
import run.al.runservice.business.service.interfaces.StravaRunService;
import run.al.runservice.persistence.model.Run;
import run.al.runservice.persistence.repository.RunRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StravaRunServiceImpl implements StravaRunService {

    private final RestClient restClient;

    private final RunRepository runRepository;

    @Value("${strava.url.base}")
    private String uriBase;

    @Value("${strava.url.api.logged_user.activities}")
    private String apiActivities;

    @Override
    @Transactional
    public List<RunDto> synchronizeStravaActivities(Long runnerId, String token) {
        Optional<Run> lastStravaRun = this.runRepository.findFirst1ByRunnerIdAndExternalSystemIdNotNullOrderByStartDateDesc(runnerId);
        String queryParams = lastStravaRun.isPresent() ? "?after=" + (lastStravaRun.get().getStartDate().getTime()/1000L) : "";
        ResponseEntity<RunDto[]> stravaRuns = restClient.get()
                .uri(uriBase + apiActivities + queryParams)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(RunDto[].class);
        List<RunDto> savedRunsToReturn = new ArrayList<>();
        Arrays.stream(stravaRuns.getBody()).filter(runDto -> StringUtils.equalsIgnoreCase(runDto.getStravaActivityType(), "run"))
                .forEach(stravaRunDto -> {
                    Run runToSave = new Run();
                    runToSave.setRunnerId(runnerId);
                    RunDto runDtoToReturn = new RunDto();
                    BeanUtils.copyProperties(stravaRunDto, runToSave);
                    Run savedRun = this.runRepository.save(runToSave);
                    BeanUtils.copyProperties(savedRun, runDtoToReturn);
                    savedRunsToReturn.add(runDtoToReturn);
                });
        return savedRunsToReturn;
    }


}
