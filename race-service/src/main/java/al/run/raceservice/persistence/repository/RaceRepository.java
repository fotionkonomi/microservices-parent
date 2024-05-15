package al.run.raceservice.persistence.repository;

import al.run.raceservice.persistence.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceRepository extends JpaRepository<Race, Long> {
}
