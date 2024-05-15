package al.run.inventoryservice.rest.controller;

import al.run.inventoryservice.rest.dto.request.InventoryStockRequest;
import al.run.inventoryservice.rest.dto.response.InventoryStockResponse;
import al.run.inventoryservice.business.dto.InventoryDto;
import al.run.inventoryservice.business.service.interfaces.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("/save")
    public ResponseEntity<InventoryDto> save(@RequestBody InventoryDto inventoryDto) {
        return ResponseEntity.ok(this.inventoryService.save(inventoryDto));
    }

    @PostMapping("/stockAvailability")
    public ResponseEntity<List<InventoryStockResponse>> getStockForListOfRaces(@RequestBody InventoryStockRequest inventoryStockRequest) {
        return ResponseEntity.ok(inventoryService.getStockAvailabilityOfRaces(inventoryStockRequest.getRaceQuantitiesRequests()));
    }

    @PostMapping("/deduct/stock")
    public ResponseEntity<Void> updateRaceQuantities(@RequestBody InventoryStockRequest inventoryStockRequest) {
        inventoryService.updateRaceQuantity(inventoryStockRequest.getRaceQuantitiesRequests());
        return ResponseEntity.ok().build();
    }

}
