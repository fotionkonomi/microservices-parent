package al.run.challengeservice.business.service.impl;

import al.run.challengeservice.business.dto.*;
import al.run.challengeservice.business.events.RunSavedEvent;
import al.run.challengeservice.business.events.UserChallengeUpdated;
import al.run.challengeservice.business.events.producer.UserChallengeUpdatedSender;
import al.run.challengeservice.business.exception.ChallengeNotFoundException;
import al.run.challengeservice.business.service.ChallengeService;
import al.run.challengeservice.persistence.entity.Challenge;
import al.run.challengeservice.persistence.entity.ChallengeCriteria;
import al.run.challengeservice.persistence.entity.CriteriaProgress;
import al.run.challengeservice.persistence.entity.UserChallenge;
import al.run.challengeservice.persistence.entity.enums.CriteriaType;
import al.run.challengeservice.persistence.entity.enums.UserChallengeStatus;
import al.run.challengeservice.persistence.repository.ChallengeRepository;
import al.run.challengeservice.persistence.repository.UserChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserChallengeRepository userChallengeRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    private final UserChallengeUpdatedSender userChallengeUpdatedSender;

    @Override
    public ChallengeDto saveChallenge(ChallengeDto challengeDto) {
        Challenge challengeMapped = modelMapper.map(challengeDto, Challenge.class);
        return modelMapper.map(challengeRepository.save(challengeMapped), ChallengeDto.class);
    }

    @Override
    public UserChallengeDto registerUserToChallenge(RegisterUserToChallengeDto registerUserToChallengeDto) {
        Challenge challenge = this.challengeRepository.findById(registerUserToChallengeDto.getChallengeId()).orElseThrow(() -> new ChallengeNotFoundException(registerUserToChallengeDto.getChallengeId()));
        UserChallenge userChallenge = new UserChallenge();
        userChallenge.setUserId(registerUserToChallengeDto.getUserId());
        userChallenge.setChallenge(challenge);
        if (new Date().before(challenge.getStartDate())) {
            userChallenge.setStatus(UserChallengeStatus.REGISTERED_NOT_STARTED);
            userChallenge.setStartDate(challenge.getStartDate());
        } else {
            userChallenge.setStartDate(new Date());
        }
        userChallenge.setEndDate(null);


        SearchRunsBetweenDatesDto searchRunsBetweenDatesDto = new SearchRunsBetweenDatesDto();
        searchRunsBetweenDatesDto.setRunnerId(registerUserToChallengeDto.getUserId());
        searchRunsBetweenDatesDto.setDateFrom(challenge.getStartDate());
        searchRunsBetweenDatesDto.setDateUntil(challenge.getEndDate());
        RunDto[] runsForChallenge = restTemplate.postForObject("http://run-service/api/run/between/dates", searchRunsBetweenDatesDto, RunDto[].class);

        List<CriteriaProgress> criteriaProgressList = new ArrayList<>();
        challenge.getCriterias().forEach(criteria -> {
            CriteriaProgress criteriaProgress = new CriteriaProgress();
            criteriaProgress.setCriteria(criteria);
            trackProgress(Arrays.stream(runsForChallenge).toList(), criteria, criteriaProgress);
            criteriaProgressList.add(criteriaProgress);
        });
        if (runsForChallenge.length > 0) {
            if (criteriaProgressList.stream().allMatch(criteriaProgress -> criteriaProgress.getProgressValue().equals(100.0))) {
                userChallenge.setStatus(UserChallengeStatus.COMPLETED);
                userChallenge.setEndDate(new Date());
            } else {
                userChallenge.setStatus(UserChallengeStatus.IN_PROGRESS);
            }
        }
        userChallenge.setCriteriaProgress(criteriaProgressList);
        UserChallenge userChallengeSaved = saveUserChallenge("userChallengeCreated", userChallenge);

        return modelMapper.map(userChallengeSaved, UserChallengeDto.class);

    }

    @Override
    public void checkChallengeEndAndUpdate() {
        List<Challenge> endedChallenges = challengeRepository.findAllByEndDateBeforeAndCheckedAfterEndFalse(new Date());
        if (!CollectionUtils.isEmpty(endedChallenges)) {
            endedChallenges.forEach(challenge -> {
                List<UserChallenge> notCompletedUserChallenges = userChallengeRepository.findAllByStatus(UserChallengeStatus.IN_PROGRESS);
                notCompletedUserChallenges.forEach(userChallenge -> {
                    userChallenge.setStatus(UserChallengeStatus.FAILED);
                    userChallenge.setEndDate(challenge.getEndDate());
                    saveUserChallenge("userChallengeUpdated", userChallenge);
                });
                challenge.setCheckedAfterEnd(true);
                challengeRepository.save(challenge);
            });
        }
    }

    @Override
    public void checkChallengeStartAndUpdate() {
        List<Challenge> startedChallenges = challengeRepository.findAllByStartDateBeforeAndCheckedAfterStartFalse(new Date());
        if (!CollectionUtils.isEmpty(startedChallenges)) {
            startedChallenges.forEach(challenge -> {
                List<UserChallenge> notCompletedUserChallenges = userChallengeRepository.findAllByStatus(UserChallengeStatus.REGISTERED_NOT_STARTED);
                notCompletedUserChallenges.forEach(userChallenge -> {
                    userChallenge.setStatus(UserChallengeStatus.IN_PROGRESS);
                    saveUserChallenge("userChallengeUpdated", userChallenge);
                });
                challenge.setCheckedAfterStart(true);
                challengeRepository.save(challenge);
            });
        }
    }

    @Override
    public void trackUpdate(RunSavedEvent runSavedEvent) {
        List<UserChallenge> userChallenges = userChallengeRepository.findAllByUserIdAndStatusAndChallenge_StartDateBeforeAndChallenge_EndDateAfter(runSavedEvent.getRunDto().getRunnerId(), UserChallengeStatus.IN_PROGRESS, runSavedEvent.getRunDto().getStartDate(), runSavedEvent.getRunDto().getStartDate());
        userChallenges.forEach(userChallenge -> {
            userChallenge.getCriteriaProgress().forEach(criteriaProgress -> {
                trackProgress(Collections.singletonList(runSavedEvent.getRunDto()), criteriaProgress.getCriteria(), criteriaProgress);
            });
            if (userChallenge.getCriteriaProgress().stream().allMatch(criteriaProgress -> criteriaProgress.getProgressValue().equals(100.0))) {
                userChallenge.setEndDate(new Date());
                userChallenge.setStatus(UserChallengeStatus.COMPLETED);
            } else {
                userChallenge.setStatus(UserChallengeStatus.IN_PROGRESS);
            }

            saveUserChallenge("userChallengeUpdated", userChallenge);
        });
    }

    private UserChallenge saveUserChallenge(String kafkaTopic, UserChallenge userChallenge) {
        UserChallenge userChallengeSaved = userChallengeRepository.save(userChallenge);
        userChallengeUpdatedSender.send(kafkaTopic, userChallengeSaved);
        return userChallengeSaved;
    }

    private void trackProgress(List<RunDto> validRunsForChallenge, ChallengeCriteria criteria, CriteriaProgress criteriaProgress) {
        if (CriteriaType.DISTANCE.equals(criteria.getType())) {
            double totalDistance = validRunsForChallenge.stream().map(RunDto::getDistance).reduce(0.0, Double::sum);
            criteriaProgress.setQuantity(criteriaProgress.getQuantity() + totalDistance);
            if (criteriaProgress.getQuantity() > criteria.getQuantity()) {
                criteriaProgress.setProgressValue(100.0);
            } else {
                criteriaProgress.setProgressValue((criteriaProgress.getQuantity() / criteria.getQuantity()) * 100.0);
            }
        }
        else if (CriteriaType.TIME.equals(criteria.getType())) {
            int totalTime = validRunsForChallenge.stream().map(RunDto::getMovingTime).reduce(0, Integer::sum);
            criteriaProgress.setQuantity(criteriaProgress.getQuantity() + (double) totalTime);
            if (criteriaProgress.getQuantity() > criteria.getQuantity()) {
                criteriaProgress.setProgressValue(100.0);
            } else {
                criteriaProgress.setProgressValue((criteriaProgress.getQuantity() / (criteria.getQuantity())) * 100.0);
            }
        }
        else if (CriteriaType.FREQUENCY.equals(criteria.getType())) {
            int frequency = validRunsForChallenge.size();
            criteriaProgress.setQuantity(criteriaProgress.getQuantity() + (double) frequency);
            if (criteriaProgress.getQuantity() > criteria.getQuantity()) {
                criteriaProgress.setProgressValue(100.0);
            } else {
                criteriaProgress.setProgressValue((criteriaProgress.getQuantity() / criteria.getQuantity()) * 100.0);
            }
        }
        else if (CriteriaType.ELEVATION.equals(criteria.getType())) {
            double totalElevation = validRunsForChallenge.stream().map(RunDto::getElevationGain).reduce(0.0, Double::sum);
            criteriaProgress.setQuantity(criteriaProgress.getQuantity() + totalElevation);
            if (criteriaProgress.getQuantity() > criteria.getQuantity()) {
                criteriaProgress.setProgressValue(100.0);
            } else {
                criteriaProgress.setProgressValue((criteriaProgress.getQuantity() / criteria.getQuantity()) * 100.0);
            }
        }
    }
}
