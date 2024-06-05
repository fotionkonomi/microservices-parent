package al.run.notificationservicemodule.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserChallengeDto {
    private Long userChallengeId;
    private Long userId;
    private ChallengeDto challenge;
    private UserChallengeStatus status;
    private Date startDate;
    private Date endDate;
    private List<CriteriaProgressDto> criteriaProgress = new ArrayList<>();
}
