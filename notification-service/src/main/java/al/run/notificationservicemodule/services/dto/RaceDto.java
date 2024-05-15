package al.run.notificationservicemodule.services.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RaceDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Double distance;
    private String location;
    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:SS")
    private Date startTime;
    private Integer elevationGain;
}
