package run.al.runservice.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class RunDto {

    private Long runId;

    private String name;

    private Double distance;

    private Integer movingTime;

    private Integer elapsedTime;

    private Double elevationGain;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Date startDate;

    private Double averageHeartRate;

    private Long externalSystemId;

    private String stravaActivityType;

    private Long runnerId;

}
