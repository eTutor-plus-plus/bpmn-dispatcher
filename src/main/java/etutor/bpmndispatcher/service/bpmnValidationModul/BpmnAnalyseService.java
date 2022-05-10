package etutor.bpmndispatcher.service.bpmnValidationModul;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import etutor.bpmndispatcher.evaluation.Analysis;
import etutor.bpmndispatcher.evaluation.DefaultAnalysis;
import etutor.bpmndispatcher.rest.dto.entities.Submission;
import etutor.bpmndispatcher.rest.dto.entities.TestConfigDTO;
import etutor.bpmndispatcher.rest.dto.entities.TestEngineDTO;
import etutor.bpmndispatcher.rest.dto.repositories.TestConfigDTORepository;
import etutor.bpmndispatcher.service.ExerciseNotValidException;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;

@Service
public class BpmnAnalyseService {
    private final Logger logger;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BpmnDeploymentService bpmnDeploymentService;
    private final BpmnTestEngineConnector bpmnTestEngineConnector;
    private final TestConfigDTORepository testConfigDTORepository;

    private String currentId;
    private String currentDefinitionId;
    private boolean deploymentSuccesful;

    public BpmnAnalyseService(BpmnDeploymentService bpmnDeploymentService, BpmnTestEngineConnector bpmnTestEngineConnector, TestConfigDTORepository testConfigDTORepository) {
        this.bpmnDeploymentService = bpmnDeploymentService;
        this.bpmnTestEngineConnector = bpmnTestEngineConnector;
        this.testConfigDTORepository = testConfigDTORepository;
        this.logger = (Logger) LoggerFactory.getLogger(BpmnAnalyseService.class);
    }

    public Analysis analyze(Submission submission, Locale locale) throws Exception {
        currentId = "";
        currentDefinitionId = "";
        deploymentSuccesful = true;
        Analysis analysis = new DefaultAnalysis();
        analysis.setSubmission(submission);
        // add Deploy Process
        this.deploySubmissionBpmn(submission);
//        if (deployementResult.containsKey(false)) {
//            // TODO create default Analysis with hint
//        }
        // add getTestconfig by submission exerciseid
        TestEngineDTO result = this.bpmnTestEngineConnector.startTest(currentDefinitionId, this.fetchTestConfig(submission.getExerciseId()));
        logger.info(result.toString());
//        logger.info(this.fetchTestConfig(submission.getExerciseId()).toString());
//        this.bpmnTestEngineConnector.startTest()
        // TODO get canReachLastTask by this grade
        // TODO optional make hints for the exercise
        // TODO optional use parallel and xor gateway control

        // remove process instance
        if (deploymentSuccesful) {
            boolean removeProcessInstance = this.removeProcessInstance(currentDefinitionId);
            logger.info(deploymentSuccesful + "----" + removeProcessInstance);
        }
        return analysis;
    }

    private void deploySubmissionBpmn(Submission submission) throws ExerciseNotValidException {
        String xml = submission.getPassedAttributes().get("xml");
        String result = null;
        if (xml == null || xml.isBlank()) throw new ExerciseNotValidException("no Bpmn in Submission");
        try {
            result = bpmnDeploymentService.deployNewBpmnWithString(xml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (result.contains("ParseException")) {
            this.deploymentSuccesful = false;
        } else {
            this.deploymentSuccesful = true;
            try {
                JSONObject obj = new JSONObject(result);
                String id = obj.getString("id");
                logger.info("ID:+++++++" + id);
                // TODO get all deployedProcessDefinitions
                currentDefinitionId = obj.getJSONObject("deployedProcessDefinitions").keys().next().toString();
                logger.info(currentDefinitionId);
                logger.info("---Deployed!---");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private TestConfigDTO fetchTestConfig(int exerciseId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/bpmn/exercise/solution/id/" + exerciseId))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), TestConfigDTO.class);
    }

    private boolean removeProcessInstance(String id) {
        try {
            return this.bpmnDeploymentService.deleteProcess(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
