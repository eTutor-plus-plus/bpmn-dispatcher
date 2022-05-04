package etutor.bpmndispatcher.rest.dto.repositories;

import etutor.bpmndispatcher.rest.dto.entities.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for manipulating Submissions in the database
 */
public interface SubmissionRepository extends JpaRepository<Submission, String> {
}
