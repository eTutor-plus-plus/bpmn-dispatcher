package etutor.bpmndispatcher.rest.dto.repositories;

import etutor.bpmndispatcher.rest.dto.entities.BpmnExcercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BpmnExcerciseRepository extends JpaRepository<BpmnExcercise, Integer> {
}
