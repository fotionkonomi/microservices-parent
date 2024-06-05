package al.run.challengeservice.business.events;

import al.run.challengeservice.business.dto.RunDto;
import lombok.Data;

@Data
public class RunSavedEvent {
    private RunDto runDto;
}
