package al.run.challengeservice.business.dto;

import al.run.challengeservice.persistence.entity.enums.CriteriaType;
import lombok.Data;

@Data
public class ChallengeCriteriaDto {
    private Long criteriaId;
    private CriteriaType type;
    private int quantity;
}
