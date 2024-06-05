package al.run.challengeservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CriteriaProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long criteriaProgressId;

    @ManyToOne
    @JoinColumn(name = "criteria_id")
    private ChallengeCriteria criteria;
    private Double progressValue = 0.0;
    private Double quantity = 0.0;
}
