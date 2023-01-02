package at.jku.dke.etutor.bpmn.dispatcher.rest.dto.repositories;

import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities.TestConfigDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestConfigDTORepository extends JpaRepository<TestConfigDTO, Integer> {
}
