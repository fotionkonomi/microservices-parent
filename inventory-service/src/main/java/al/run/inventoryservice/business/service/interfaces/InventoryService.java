package al.run.inventoryservice.business.service.interfaces;

import al.run.inventoryservice.rest.dto.request.RaceQuantityRequest;
import al.run.inventoryservice.rest.dto.response.InventoryStockResponse;
import al.run.inventoryservice.business.dto.InventoryDto;

import java.util.List;

public interface InventoryService {

    InventoryDto save(InventoryDto inventoryDto);

    List<InventoryStockResponse> getStockAvailabilityOfRaces(List<RaceQuantityRequest> raceQuantityRequests);

    void updateRaceQuantity(List<RaceQuantityRequest> raceQuantityRequests);
}
