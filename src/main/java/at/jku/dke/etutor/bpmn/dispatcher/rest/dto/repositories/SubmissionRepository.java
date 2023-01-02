package at.jku.dke.etutor.bpmn.dispatcher.rest.dto.repositories;

import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for manipulating Submissions in the database
 */
public interface SubmissionRepository extends JpaRepository<Submission, String> {
}
