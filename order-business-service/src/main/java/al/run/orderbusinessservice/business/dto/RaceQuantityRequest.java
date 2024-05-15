package al.run.orderbusinessservice.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceQuantityRequest {
    private Long raceId;
    private int quantity;
}
