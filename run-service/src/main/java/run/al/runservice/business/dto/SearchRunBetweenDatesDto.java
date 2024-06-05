package run.al.runservice.business.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SearchRunBetweenDatesDto {
    private Long runnerId;
    private Date dateFrom;
    private Date dateUntil;
}
