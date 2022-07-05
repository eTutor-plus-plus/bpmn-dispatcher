package etutor.bpmndispatcher.service;

import ch.qos.logback.classic.Logger;
import etutor.bpmndispatcher.config.ApplicationProperties;
import etutor.bpmndispatcher.evaluation.Analysis;
import etutor.bpmndispatcher.evaluation.DefaultReport;
import etutor.bpmndispatcher.evaluation.Grading;
import etutor.bpmndispatcher.rest.dto.entities.GradingDTO;
import etutor.bpmndispatcher.rest.dto.entities.ReportDTO;
import etutor.bpmndispatcher.rest.dto.entities.Submission;
import etutor.bpmndispatcher.service.bpmnValidationModul.BpmnModuleService;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Locale;


/**
 * Is used to forward the submission to the corresponding module
 */
@Service
public class SubmissionDispatcherService {
    private final Logger logger;
    private final RepositoryService repositoryService;
    private final BpmnModuleService bpmnModuleService;
    private final ApplicationProperties applicationProperties;
    private final ReportService reportService;

    public SubmissionDispatcherService(RepositoryService repositoryService, BpmnModuleService bpmnModuleService, ApplicationProperties applicationProperties, ReportService reportService) {
        this.bpmnModuleService = bpmnModuleService;
        this.applicationProperties = applicationProperties;
        this.reportService = reportService;
        this.logger = (Logger) LoggerFactory.getLogger(SubmissionDispatcherService.class);
        this.repositoryService = repositoryService;
    }

    /**
     * Identifies the module according to submission.taskType
     * and calls the modules' implementations for evaluating the submission.
     * Persists the entities (submission, report, grading)
     */
    @Async("asyncExecutor")
    public void run(Submission submission, Locale locale) {
        logger.warn("submission " + submission.getMaxPoints());
        logger.debug("Saving submission to database");
        repositoryService.persistSubmission(submission);
        logger.debug("Finished saving submission to database");
        try {
            logger.debug("Analyzing submission");
            Analysis analysis = getAnalysis(submission, locale);
            logger.debug("Finished analyzing submission");
            logger.debug("Grading submission");
            Grading grading = getGrading(analysis, submission);
            logger.debug("Finished grading submission");
            logger.debug("Finished evaluating submission");

            GradingDTO gradingDTO = new GradingDTO(submission.getSubmissionId(), grading);
//            gradingDTO.setResult(evaluator.generateHTMLResult( analysis, submission.getPassedAttributes(), locale));

            if ((grading.getPoints() < grading.getMaxPoints() || grading.getPoints() == 0)) {
                logger.info("Requesting report");
                DefaultReport report = getReport(grading, analysis, locale);
                logger.debug("Received report");
                gradingDTO.setReport(new ReportDTO(submission.getSubmissionId(), report));
            }
            persistGrading(gradingDTO);
        } catch (Exception e) {
            logger.warn("Stopped Evaluation due to errors", e);
        }
    }

    /**
     * Calls the analyze() method of the provided Evaluator
     *
     * @param submission the submission
     * @return the Analysis
     * @throws Exception if an error occurs
     */
    public Analysis getAnalysis(Submission submission, Locale locale) throws Exception {
        return this.bpmnModuleService.analyze(submission, locale);
    }


    /**
     * Calls the grade() method of the provided evaluator
     *
     * @param analysis   the analysis
     * @param submission the submission
     * @return the Grading
     * @throws Exception if an error occurs
     */
    public Grading getGrading(Analysis analysis, Submission submission) throws Exception {
        return this.bpmnModuleService.grade(analysis, submission);
    }


    /**
     * Calls the report() method of the provided evaluator
     *
     * @param grading  the grading
     * @param analysis the analysis
     * @param locale   the locale
     * @return the report
     * @throws Exception if an error occurs
     */
    public DefaultReport getReport(Grading grading, Analysis analysis, Locale locale) throws Exception {
        return this.reportService.report(analysis, grading);
    }


    /**
     * Persists the grading
     *
     * @param gradingDTO the grading
     */
    public void persistGrading(GradingDTO gradingDTO) {
        try {
            logger.debug("Saving grading to database");
            repositoryService.persistGrading(gradingDTO);
            logger.debug("Finished saving grading to database");
        } catch (Exception e) {
            logger.error("Could not save grading");
        }
    }
}
