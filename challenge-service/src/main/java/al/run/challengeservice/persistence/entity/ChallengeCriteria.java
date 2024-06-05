package al.run.challengeservice.persistence.entity;

import al.run.challengeservice.persistence.entity.enums.CriteriaType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ChallengeCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long criteriaId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CriteriaType type;

    @Column(nullable = false)
    private int quantity;
}
