package run.al.runservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import run.al.runservice.persistence.model.Run;

import java.util.Optional;

public interface RunRepository extends JpaRepository<Run, Long> {

    Optional<Run> findFirst1ByRunnerIdAndExternalSystemIdNotNullOrderByStartDateDesc(Long runnerId);
}
