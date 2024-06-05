package al.run.challengeservice.business.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SearchRunsBetweenDatesDto {
    private Long runnerId;
    private Date dateFrom;
    private Date dateUntil;
}