package at.jku.dke.etutor.bpmn.dispatcher.rest.dto.repositories;

import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for manipulating ReportDTO instances in the database
 */
public interface ReportDTORepository extends JpaRepository<Report, String> {
}
