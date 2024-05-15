package run.al.runservice.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class RunDto {

    private Long runId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "distance")
    private Double distance;

    @JsonProperty(value = "moving_time")
    private Integer movingTime;

    @JsonProperty(value = "elapsed_time")
    private Integer elapsedTime;

    @JsonProperty(value = "total_elevation_gain")
    private Double elevationGain;

    @JsonProperty(value = "start_date")
    private Date startDate;

    @JsonProperty(value = "average_heartrate")
    private Double averageHeartRate;

    @JsonProperty(value = "id")
    private Long externalSystemId;

    @JsonProperty(value = "type")
    private String stravaActivityType;

}
