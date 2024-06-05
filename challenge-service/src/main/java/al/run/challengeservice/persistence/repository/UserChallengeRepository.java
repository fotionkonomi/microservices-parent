package al.run.challengeservice.persistence.repository;

import al.run.challengeservice.persistence.entity.UserChallenge;
import al.run.challengeservice.persistence.entity.enums.UserChallengeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {

    List<UserChallenge> findAllByStatus(UserChallengeStatus status);
    List<UserChallenge> findAllByUserIdAndStatusAndChallenge_StartDateBeforeAndChallenge_EndDateAfter(Long userId, UserChallengeStatus status, Date date, Date date1);
}
