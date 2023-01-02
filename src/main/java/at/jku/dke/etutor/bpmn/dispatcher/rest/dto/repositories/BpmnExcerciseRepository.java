package at.jku.dke.etutor.bpmn.dispatcher.rest.dto.repositories;

import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities.BpmnExcercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BpmnExcerciseRepository extends JpaRepository<BpmnExcercise, Integer> {
}
