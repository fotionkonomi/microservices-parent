package al.run.inventoryservice.business.service.implementation;

import al.run.inventoryservice.business.exceptions.NotEnoughStockException;
import al.run.inventoryservice.business.exceptions.RaceNotFoundException;
import al.run.inventoryservice.rest.dto.request.RaceQuantityRequest;
import al.run.inventoryservice.rest.dto.response.InventoryStockResponse;
import al.run.inventoryservice.business.dto.InventoryDto;
import al.run.inventoryservice.business.service.interfaces.InventoryService;
import al.run.inventoryservice.persistence.model.Inventory;
import al.run.inventoryservice.persistence.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository repository;

    @Override
    public InventoryDto save(InventoryDto inventoryDto) {
        Inventory inventoryToSave = new Inventory();

        BeanUtils.copyProperties(inventoryDto, inventoryToSave);
        Inventory savedInventory = repository.save(inventoryToSave);

        InventoryDto inventoryToReturn = new InventoryDto();
        BeanUtils.copyProperties(savedInventory, inventoryToReturn);

        return inventoryToReturn;
    }

    @Override
    public List<InventoryStockResponse> getStockAvailabilityOfRaces(List<RaceQuantityRequest> raceQuantityRequests) {
        return raceQuantityRequests.stream().map(raceQuantityRequest -> {
            InventoryStockResponse inventoryStockResponse = new InventoryStockResponse();
            inventoryStockResponse.setRaceId(raceQuantityRequest.getRaceId());
            repository.findByRaceId(raceQuantityRequest.getRaceId()).ifPresentOrElse(inventory -> inventoryStockResponse.setInStock(inventory.getQuantity() == null || inventory.getQuantity() >= raceQuantityRequest.getQuantity()),
                    () -> inventoryStockResponse.setInStock(false));
            return inventoryStockResponse;
        }).toList();
    }

    @Transactional
    @Override
    public void updateRaceQuantity(List<RaceQuantityRequest> raceQuantityRequests) {
        raceQuantityRequests.forEach(raceQuantity -> {
            repository.findByRaceId(raceQuantity.getRaceId()).map(inventory -> {
                if (inventory.getQuantity() < raceQuantity.getQuantity()) {
                    throw new NotEnoughStockException(raceQuantity.getRaceId());
                }
                inventory.setQuantity(inventory.getQuantity() - raceQuantity.getQuantity());
                return repository.save(inventory);
            }).orElseThrow(() -> new RaceNotFoundException(raceQuantity.getRaceId()));
        });
    }
}
