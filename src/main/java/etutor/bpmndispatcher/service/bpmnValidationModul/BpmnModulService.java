package etutor.bpmndispatcher.service.bpmnValidationModul;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import etutor.bpmndispatcher.evaluation.Analysis;
import etutor.bpmndispatcher.evaluation.DefaultAnalysis;
import etutor.bpmndispatcher.evaluation.DefaultGrading;
import etutor.bpmndispatcher.evaluation.Grading;
import etutor.bpmndispatcher.rest.dto.entities.Submission;
import etutor.bpmndispatcher.rest.dto.entities.TestConfigDTO;
import etutor.bpmndispatcher.rest.dto.entities.TestEngineDTO;
import etutor.bpmndispatcher.rest.dto.repositories.TestConfigDTORepository;
import etutor.bpmndispatcher.rest.dto.repositories.TestEngineDTORepository;
import etutor.bpmndispatcher.service.ExerciseNotValidException;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@Service
public class BpmnModulService {
    private final Logger logger;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BpmnDeploymentService bpmnDeploymentService;
    private final BpmnTestEngineConnector bpmnTestEngineConnector;
    //    private final TestConfigDTORepository testConfigDTORepository;
    private final TestEngineDTORepository testEngineDTORepository;
    private final ServerProperties serverProperties;


    private List<String> currentIds;
    private List<String> currentDefinitionId;
    private boolean deploymentSuccesfull;

    public BpmnModulService(BpmnDeploymentService bpmnDeploymentService, BpmnTestEngineConnector bpmnTestEngineConnector, TestConfigDTORepository testConfigDTORepository, TestEngineDTORepository testEngineDTORepository, ServerProperties serverProperties) {
        this.bpmnDeploymentService = bpmnDeploymentService;
        this.bpmnTestEngineConnector = bpmnTestEngineConnector;
//        this.testConfigDTORepository = testConfigDTORepository;
        this.testEngineDTORepository = testEngineDTORepository;
        this.serverProperties = serverProperties;
        this.logger = (Logger) LoggerFactory.getLogger(BpmnModulService.class);
    }

    public Analysis analyze(Submission submission, Locale locale) throws Exception {
        // TODO fetch submission max points from DB

        Analysis analysis = new DefaultAnalysis();
        currentIds = new ArrayList<>();
        currentDefinitionId = new ArrayList<>();
        deploymentSuccesfull = true;

        analysis.setSubmission(submission);
        // add Deploy Process
        this.deploySubmissionBpmn(submission);
//        if (deployementResult.containsKey(false)) {
//            // TODO create default Analysis with hint
//        }
        // TODO stub XML validion Modul
        return analysis;
    }

    public Grading grade(Analysis analysis, Submission submission) throws Exception {
        Grading grading = new DefaultGrading();
        grading.setMaxPoints(submission.getMaxPoints());
        TestEngineDTO result = null;

        // add getTestconfig by submission exerciseid
        for (String id : currentIds) {
            logger.info(currentIds.toString() + "------" + currentDefinitionId.toString());
            result = this.bpmnTestEngineConnector.startTest(id, this.fetchTestConfig(submission.getExerciseId()));
        }
        // get canReachLastTask by this grade
        if (result != null) {
            logger.info(result.toString());
            testEngineDTORepository.save(result);
            logger.warn("Engine" + result.getTestEngineRuntimeDTO().isProcessInOrder());
            if (result.getTestEngineRuntimeDTO().isProcessInOrder()) {
                grading.setPoints(submission.getMaxPoints());
            }
        }

        // TODO optional make hints for the exercise
        // TODO optional use parallel and xor gateway control

        // remove process instance
        if (deploymentSuccesfull) {
            for (String currentDefinitionID : currentDefinitionId) {
                this.removeProcessInstance(currentDefinitionID);
            }
        }
        return grading;
    }

    private void deploySubmissionBpmn(Submission submission) throws ExerciseNotValidException {
        String xml = submission.getPassedAttributes().get("submission");
        String result;
        if (xml == null || xml.isBlank()) throw new ExerciseNotValidException("no Bpmn in Submission");
        try {
            result = bpmnDeploymentService.deployNewBpmnWithString(xml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (result.contains("ParseException")) {
            this.deploymentSuccesfull = false;
        } else {
            this.deploymentSuccesfull = true;
            try {
                JSONObject obj = new JSONObject(result);
                String id = obj.getString("id");
//                logger.info("ID:+++++++" + id);
//                currentDefinitionId = obj.getJSONObject("deployedProcessDefinitions").keys().next().toString();
//                logger.info(currentDefinitionId);
//                logger.info("---Deployed!---");
                JSONObject responseObject = obj.getJSONObject("deployedProcessDefinitions");
                Iterator definitionID = obj.getJSONObject("deployedProcessDefinitions").keys();
                while (definitionID.hasNext()) {
                    String definitionId = definitionID.next().toString();
                    currentDefinitionId.add(definitionId);
//                    logger.info(definitionID.next().toString());
                    currentIds.add(responseObject.getJSONObject(definitionId).getString("key"));
//                    logger.info(responseObject.getJSONObject(definitionId).getString("key"));
                }
//                logger.warn(definitionID);
                logger.info("---Deployed!---");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private TestConfigDTO fetchTestConfig(int exerciseId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + serverProperties.getPort() + "/bpmn/exercise/solution/id/" + exerciseId))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) return objectMapper.readValue(response.body(), TestConfigDTO.class);
        throw new IOException("no config");
    }

    private void removeProcessInstance(String id) {
        try {
            this.bpmnDeploymentService.deleteProcess(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
