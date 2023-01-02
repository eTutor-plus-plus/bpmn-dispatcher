package at.jku.dke.etutor.bpmn.dispatcher.rest.dto.repositories;

import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities.TestEngineDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEngineDTORepository extends JpaRepository<TestEngineDTO, Long> {
}
