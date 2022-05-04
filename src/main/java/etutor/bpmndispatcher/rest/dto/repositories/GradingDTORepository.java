package etutor.bpmndispatcher.rest.dto.repositories;

import etutor.bpmndispatcher.rest.dto.entities.GradingDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for manipulating GradingDTO instances in the database
 */
public interface GradingDTORepository extends JpaRepository<GradingDTO, String> {
}
