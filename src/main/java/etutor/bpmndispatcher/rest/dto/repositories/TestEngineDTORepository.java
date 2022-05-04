package etutor.bpmndispatcher.rest.dto.repositories;

import etutor.bpmndispatcher.rest.dto.entities.TestEngineDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEngineDTORepository extends JpaRepository<TestEngineDTO, Long> {
}
