package al.run.orderbusinessservice.business.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InventoryStockRequest {
    private List<RaceQuantityRequest> raceQuantitiesRequests = new ArrayList<>();
}