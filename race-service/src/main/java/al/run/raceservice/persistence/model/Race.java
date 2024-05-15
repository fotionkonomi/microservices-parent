package al.run.raceservice.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "race")
@Data
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Double distance;
    private String location;
    private Date startTime;
    private Integer elevationGain;
}
