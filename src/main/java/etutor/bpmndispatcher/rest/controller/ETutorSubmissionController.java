package etutor.bpmndispatcher.rest.controller;


import ch.qos.logback.classic.Logger;
import etutor.bpmndispatcher.ETutorCORSPolicy;
import etutor.bpmndispatcher.rest.dto.entities.Submission;
import etutor.bpmndispatcher.rest.dto.entities.SubmissionDTO;
import etutor.bpmndispatcher.rest.dto.entities.SubmissionId;
import etutor.bpmndispatcher.rest.dto.repositories.SubmissionRepository;
import etutor.bpmndispatcher.service.SubmissionDispatcherService;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * API for posting submissions.
 */
@RestController
@RequestMapping("/submission")
@CrossOrigin(origins = ETutorCORSPolicy.CORS_POLICY)
public class ETutorSubmissionController {
    private final Logger logger;
    private final SubmissionDispatcherService submissionDispatcherService;
    private final SubmissionRepository submissionRepository;

    /**
     * The constructor
     *
     * @param submissionDispatcherService the injected SubmissionDispatcher service that handles the evaluation of a submission
     */
    public ETutorSubmissionController(SubmissionDispatcherService submissionDispatcherService, SubmissionRepository submissionRepository) {
        this.logger = (Logger) LoggerFactory.getLogger(ETutorSubmissionController.class);
        this.submissionDispatcherService = submissionDispatcherService;
        this.submissionRepository = submissionRepository;
    }

    /**
     * Takes a submission as parameter, calculates a unique submissionId, asynchronously initializes a SubmissionDispatcher and returns the generated ID
     *
     * @param submissionDto : the submission from the student
     * @return : ResponseEntity containing EntityModel with the generated submissionId and a link under which the grading can be requested
     * - HttpStatus.INTERNAL_SERVER_ERROR if exception occurs
     * - HttpStatus.ACCEPTED if submission is accepted for processing
     */
    @PostMapping("")
    public ResponseEntity<EntityModel<SubmissionId>> dispatchSubmission(@RequestBody SubmissionDTO submissionDto, @RequestHeader(value = "Accept-Language", defaultValue = "de") String language) {
        Submission submission = new Submission(submissionDto);
        logger.info("Submission received");
        SubmissionId submissionId;

        logger.info("Calculating submission-ID");
        submissionId = SubmissionId.createId(submission);
        logger.info("Finished calculating submission-ID: {}", submissionId.getSubmissionId());

        submissionDispatcherService.run(submission, mapLangToLocale(language));

        return new ResponseEntity<>(EntityModel.of(submissionId,
                linkTo(methodOn(ETutorGradingController.class).getGrading(submissionId.toString())).withRel("grading")),
                HttpStatus.ACCEPTED);
    }

    /**
     * Returns a submission
     *
     * @param submissionUUID the UUID identifying the submission
     * @return a ResponseEntity containing the submission
     */
    @GetMapping("/{submissionUUID}")
    public ResponseEntity<Submission> getSubmission(@PathVariable String submissionUUID) {
        Optional<Submission> optionalSubmission = this.submissionRepository.findById(submissionUUID);
        Submission submission = optionalSubmission.orElse(null);
        if (submission != null) return ResponseEntity.ok(submission);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


    /**
     * Maps a String to a Locale
     *
     * @param language the language
     * @return the Locale
     */
    private Locale mapLangToLocale(String language) {
        if (language.equalsIgnoreCase("de")) return Locale.GERMAN;
        else return Locale.ENGLISH;
    }
}
