package etutor.bpmndispatcher.service;


import etutor.bpmndispatcher.rest.dto.entities.GradingDTO;
import etutor.bpmndispatcher.rest.dto.entities.Submission;
import etutor.bpmndispatcher.rest.dto.repositories.GradingDTORepository;
import etutor.bpmndispatcher.rest.dto.repositories.SubmissionRepository;
import etutor.bpmndispatcher.rest.dto.repositories.TestConfigDTORepository;
import org.springframework.stereotype.Service;

/**
 * Service for handling JPA-repository-methods
 */
@Service
public class RepositoryService {
    private final GradingDTORepository gradingRepository;
    private final SubmissionRepository submissionRepository;

    private final TestConfigDTORepository testConfigDTORepository;

    /**
     * The constructor
     *
     * @param gradingRepo             the injected JPARepository for GradingDTO´s
     * @param submissionRepo          the injected JPARepository for Submissions
     * @param testConfigDTORepository
     */
    public RepositoryService(GradingDTORepository gradingRepo,
                             SubmissionRepository submissionRepo, TestConfigDTORepository testConfigDTORepository) {
        gradingRepository = gradingRepo;
        submissionRepository = submissionRepo;
        this.testConfigDTORepository = testConfigDTORepository;
    }

    /**
     * Persists a submission
     *
     * @param submission the submission
     */
    public void persistSubmission(Submission submission) {
        submissionRepository.save(submission);
    }

    /**
     * Persists a GradingDTO
     *
     * @param grading the grading
     */
    public void persistGrading(GradingDTO grading) {
        gradingRepository.save(grading);
    }

}
