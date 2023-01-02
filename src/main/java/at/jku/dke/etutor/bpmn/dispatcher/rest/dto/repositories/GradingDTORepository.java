package at.jku.dke.etutor.bpmn.dispatcher.rest.dto.repositories;

import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities.Grading;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for manipulating GradingDTO instances in the database
 */
public interface GradingDTORepository extends JpaRepository<Grading, String> {
}
