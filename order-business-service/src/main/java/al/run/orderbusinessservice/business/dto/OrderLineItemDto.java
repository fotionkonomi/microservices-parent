package al.run.orderbusinessservice.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderLineItemDto {

    private Long id;
    private Long raceId;
    private int quantity;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long userId;
    private Integer price;
}
