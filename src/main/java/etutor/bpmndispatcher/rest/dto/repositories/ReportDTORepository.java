package etutor.bpmndispatcher.rest.dto.repositories;

import etutor.bpmndispatcher.rest.dto.entities.ReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for manipulating ReportDTO instances in the database
 */
public interface ReportDTORepository extends JpaRepository<ReportDTO, String> {
}
