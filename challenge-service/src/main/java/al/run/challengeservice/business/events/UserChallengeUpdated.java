package al.run.challengeservice.business.events;

import al.run.challengeservice.business.dto.UserChallengeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserChallengeUpdated {
    private UserChallengeDto userChallengeDto;
}
