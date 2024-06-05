package al.run.challengeservice.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ChallengeDto {

    private Long challengeId;
    private String name;
    private String description;
    private List<ChallengeCriteriaDto> criterias;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Date endDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Date createdAt;
}
