package al.run.challengeservice.rest.controller;

import al.run.challengeservice.business.dto.ChallengeDto;
import al.run.challengeservice.business.dto.RegisterUserToChallengeDto;
import al.run.challengeservice.business.dto.UserChallengeDto;
import al.run.challengeservice.business.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/challenge")
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<ChallengeDto> saveChallenge(@RequestBody ChallengeDto challengeDto) {
        return ResponseEntity.ok(challengeService.saveChallenge(challengeDto));
    }

    @PostMapping("/register/user")
    public ResponseEntity<UserChallengeDto> saveUserChallenge(@RequestBody RegisterUserToChallengeDto registerUserToChallengeDto, @RequestHeader("loggedInUser") Long loggedInUser) {
        registerUserToChallengeDto.setUserId(loggedInUser);
        return ResponseEntity.ok(challengeService.registerUserToChallenge(registerUserToChallengeDto));
    }

}
