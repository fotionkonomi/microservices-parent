package run.al.runservice.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "run")
@Data
public class Run {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long runId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double distance;

    @Column(nullable = false)
    private Integer movingTime;

    @Column(nullable = false)
    private Integer elapsedTime;

    private Double elevationGain;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    private Double averageHeartRate;

    private Long externalSystemId;

    @Column(nullable = false)
    private Long runnerId;

}
