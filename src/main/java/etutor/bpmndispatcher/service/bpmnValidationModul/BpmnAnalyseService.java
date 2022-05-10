package etutor.bpmndispatcher.service.bpmnValidationModul;

import ch.qos.logback.classic.Logger;
import etutor.bpmndispatcher.evaluation.Analysis;
import etutor.bpmndispatcher.evaluation.DefaultAnalysis;
import etutor.bpmndispatcher.rest.dto.entities.Submission;
import etutor.bpmndispatcher.rest.dto.entities.TestConfig;
import etutor.bpmndispatcher.rest.dto.entities.TestConfigDTO;
import etutor.bpmndispatcher.rest.dto.repositories.TestConfigDTORepository;
import etutor.bpmndispatcher.service.ExerciseNotValidException;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

@Service
public class BpmnAnalyseService {
    private final Logger logger;
    private final BpmnDeploymentService bpmnDeploymentService;
    private final BpmnTestEngineConnector bpmnTestEngineConnector;
    private final TestConfigDTORepository testConfigDTORepository;
    @PersistenceContext
    private EntityManager entityManager;

    public BpmnAnalyseService(BpmnDeploymentService bpmnDeploymentService, BpmnTestEngineConnector bpmnTestEngineConnector, TestConfigDTORepository testConfigDTORepository) {
        this.bpmnDeploymentService = bpmnDeploymentService;
        this.bpmnTestEngineConnector = bpmnTestEngineConnector;
        this.testConfigDTORepository = testConfigDTORepository;
        this.logger = (Logger) LoggerFactory.getLogger(BpmnAnalyseService.class);
    }

    public Analysis analyze(Submission submission, Locale locale) throws Exception {
        Analysis analysis = new DefaultAnalysis();
        analysis.setSubmission(submission);
        // add Deploy Process
        Map<Boolean, String> deployementResult = this.deploySubmissionBpmn(submission);
//        if (deployementResult.containsKey(false)) {
//            // TODO create default Analysis with hint
//        }
        // add getTestconfig by submission exerciseid
//        TestEngineDTO result = this.bpmnTestEngineConnector.startTest("sdfsd", this.fetchTestConfig(submission.getExerciseId()));
        logger.info(this.fetchTestConfig(submission.getExerciseId()).toString());
//        this.bpmnTestEngineConnector.startTest()
        // TODO get canReachLastTask by this grade
        // TODO optional make hints for the exercise
        // TODO optional use parallel and xor gateway control

        // remove process instance
        String id = deployementResult.get(true);
        boolean removeProcessInstance = this.removeProcessInstance(id);
        logger.info(deployementResult + "----" + removeProcessInstance);
        return analysis;
    }

    private Map<Boolean, String> deploySubmissionBpmn(Submission submission) throws ExerciseNotValidException {
        String xml = submission.getPassedAttributes().get("xml");
        String result = null;
        if (xml == null || xml.isBlank()) throw new ExerciseNotValidException("no Bpmn in Submission");
        try {
            result = bpmnDeploymentService.deployNewBpmnWithString(xml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (result.contains("ParseException")) {
            return Map.of(false, result);
        } else {
            try {
                JSONObject obj = new JSONObject(result);
                String id = obj.getString("id");
                logger.info("ID:+++++++" + id);
                String definitionID = obj.getJSONObject("deployedProcessDefinitions").keys().next().toString();
                logger.warn(definitionID);
                logger.info("---Deployed!---");
                return Map.of(true, definitionID);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private TestConfig fetchTestConfig(int exerciseId) {
        TestConfigDTO testConfigDTO = this.testConfigDTORepository.findById(exerciseId).orElse(null);
        if (testConfigDTO != null) return new TestConfig(testConfigDTO);
        return null;
    }

    private boolean removeProcessInstance(String id) {
        try {
            return this.bpmnDeploymentService.deleteProcess(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
