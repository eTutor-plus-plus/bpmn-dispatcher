package etutor.bpmndispatcher.rest.dto.repositories;

import etutor.bpmndispatcher.rest.dto.entities.TestConfigDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestConfigDTORepository extends JpaRepository<TestConfigDTO, Long> {
}
