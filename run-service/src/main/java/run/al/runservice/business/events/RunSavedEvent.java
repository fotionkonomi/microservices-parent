package run.al.runservice.business.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import run.al.runservice.business.dto.RunDto;

@Data
@AllArgsConstructor
public class RunSavedEvent {

    private RunDto runDto;
}
