package al.run.challengeservice.persistence.repository;

import al.run.challengeservice.persistence.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    List<Challenge> findAllByEndDateBeforeAndCheckedAfterEndFalse(Date date);
    List<Challenge> findAllByStartDateBeforeAndCheckedAfterStartFalse(Date date);
}
