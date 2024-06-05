package run.al.runservice.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import run.al.runservice.business.dto.RunDto;
import run.al.runservice.business.dto.StravaRunDto;
import run.al.runservice.business.service.interfaces.RunService;
import run.al.runservice.business.service.interfaces.StravaRunService;
import run.al.runservice.persistence.model.Run;
import run.al.runservice.persistence.repository.RunRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StravaRunServiceImpl implements StravaRunService {

    private final RestClient restClient;

    private final RunRepository runRepository;

    private final RunService runService;

    private final ModelMapper modelMapper;

    @Value("${strava.url.base}")
    private String uriBase;

    @Value("${strava.url.api.logged_user.activities}")
    private String apiActivities;

    @Override
    @Transactional
    public List<RunDto> synchronizeStravaActivities(Long runnerId, String token) {
        Optional<Run> lastStravaRun = this.runRepository.findFirst1ByRunnerIdAndExternalSystemIdNotNullOrderByStartDateDesc(runnerId);
        String queryParams = lastStravaRun.isPresent() ? "?after=" + (lastStravaRun.get().getStartDate().getTime()/1000L) : "";
        ResponseEntity<StravaRunDto[]> stravaRuns = restClient.get()
                .uri(uriBase + apiActivities + queryParams)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toEntity(StravaRunDto[].class);
        List<RunDto> savedRunsToReturn = new ArrayList<>();
        Arrays.stream(stravaRuns.getBody()).filter(runDto -> StringUtils.equalsIgnoreCase(runDto.getStravaActivityType(), "run"))
                .forEach(stravaRunDto -> {
                    RunDto runDtoToReturn = this.runService.saveRun(runnerId, modelMapper.map(stravaRunDto, RunDto.class));
                    savedRunsToReturn.add(runDtoToReturn);
                });
        return savedRunsToReturn;
    }


}
