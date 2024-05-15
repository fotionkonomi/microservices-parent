package al.run.notificationservicemodule.events;

import lombok.Data;

@Data
public class OrderPlacedEvent {

    private Long orderId;
    private Long userId;
}
