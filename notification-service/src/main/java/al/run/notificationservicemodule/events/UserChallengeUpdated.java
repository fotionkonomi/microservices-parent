package al.run.notificationservicemodule.events;

import al.run.notificationservicemodule.dto.UserChallengeDto;
import lombok.Data;

@Data
public class UserChallengeUpdated {
    private UserChallengeDto userChallengeDto;
}
