package al.run.notificationservicemodule.dto;

import lombok.Data;

@Data
public class ChallengeCriteriaDto {
    private Long criteriaId;
    private CriteriaType type;
    private int quantity;
}
