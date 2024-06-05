package al.run.challengeservice.business.dto;

import lombok.Data;

@Data
public class CriteriaProgressDto {
    private Long criteriaProgressId;
    private ChallengeCriteriaDto criteria;
    private Double progressValue;
    private Double quantity;

}
