package at.jku.dke.etutor.bpmn.dispatcher.service.bpmnValidationModul;

import at.jku.dke.etutor.bpmn.dispatcher.evaluation.Analysis;
import at.jku.dke.etutor.bpmn.dispatcher.evaluation.BpmnAnalysis;
import at.jku.dke.etutor.bpmn.dispatcher.evaluation.DefaultGrading;
import at.jku.dke.etutor.bpmn.dispatcher.evaluation.Grading;
import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities.BpmnExcercise;
import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities.TestConfigDTO;
import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities.TestEngineDTO;
import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.repositories.TestEngineDTORepository;
import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities.Submission;
import at.jku.dke.etutor.bpmn.dispatcher.service.ExerciseNotValidException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ServerProperties;
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
public class BpmnModuleService {
    private final Logger logger;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BpmnDeploymentService bpmnDeploymentService;
    private final BpmnTestEngineConnector bpmnTestEngineConnector;
    // private final TestConfigDTORepository testConfigDTORepository;
    private final TestEngineDTORepository testEngineDTORepository;
    private final ServerProperties serverProperties;
    private final HttpClient client = HttpClient.newHttpClient();

    public BpmnModuleService(BpmnDeploymentService bpmnDeploymentService, BpmnTestEngineConnector bpmnTestEngineConnector, TestEngineDTORepository testEngineDTORepository, ServerProperties serverProperties) {
        this.bpmnDeploymentService = bpmnDeploymentService;
        this.bpmnTestEngineConnector = bpmnTestEngineConnector;
        this.testEngineDTORepository = testEngineDTORepository;
        this.serverProperties = serverProperties;
        this.logger = (Logger) LoggerFactory.getLogger(BpmnModuleService.class);
    }

    public Analysis analyze(Submission submission, Locale locale) throws Exception {
        Analysis analysis = new BpmnAnalysis();
        analysis.setSubmission(submission);
        // add Deploy Process
        this.deploySubmissionBpmn(submission, analysis);
        // TODO stub XML validion Modul
        return analysis;
    }

    public Grading grade(Analysis analysis, Submission submission) throws Exception {
        if (!(analysis instanceof BpmnAnalysis)) throw new ExerciseNotValidException("wrong Analysistyp");
        Grading grading = new DefaultGrading();
//        grading.setMaxPoints(submission.getMaxPoints());
        if (((BpmnAnalysis) analysis).isDeploymentSuccesful()) {
            grading.setMaxPoints(1);
            List<TestEngineDTO> result = new ArrayList<>();


            // add getTestconfig by submission exerciseid
            for (String id : ((BpmnAnalysis) analysis).getCurrentIds()) {
                //                logger.info(((BpmnAnalysis) analysis).getCurrentIds().toString() + "------" + ((BpmnAnalysis) analysis).getCurrentDefinitionId().toString());
                //                TestConfigDTO configDTO = this.fetchTestConfig(submission.getExerciseId());
                //                result = this.bpmnTestEngineConnector.startTest(id, configDTO);
                List<TestConfigDTO> configDTO = this.fetchBpmnConfig(submission.getExerciseId());
                for (TestConfigDTO config : configDTO) {
//                    result.add(this.bpmnTestEngineConnector.startTest(id, config));
                    TestEngineDTO response = this.bpmnTestEngineConnector.startTest(id, config);
                    if (this.gradeTestEngine(response, config)) {
                        grading.setPoints(grading.getMaxPoints());
                    } else {
                        grading.setPoints(0);
                        break;
                    }
                    this.testEngineDTORepository.save(response);
//                    logger.info("Result: " + result);
                }
                // get canReachLastTask by this grade
//                testEngineDTORepository.saveAll(result);
//                if (this.gradeByTestEngine(result, config)) {
//                    grading.setPoints(grading.getMaxPoints());
//                } else {
//                    grading.setPoints(0);
//                    break;
//                }
            }
            // TODO optional make hints for the exercise
            // TODO optional use parallel and xor gateway control

            // remove process instance
            if (((BpmnAnalysis) analysis).isDeploymentSuccesful()) {
                for (String currentDefinitionID : ((BpmnAnalysis) analysis).getCurrentDefinitionId()) {
                    this.removeProcessInstance(currentDefinitionID);
                }
            }
        }
        return grading;
    }

    private void deploySubmissionBpmn(Submission submission, Analysis analysis) throws Exception {
        if (!(analysis instanceof BpmnAnalysis)) throw new ExerciseNotValidException("wrong analysis typ");
        String xml = submission.getPassedAttributes().get("submission");
        String result;
        if (xml == null || xml.isBlank()) throw new ExerciseNotValidException("no Bpmn in Submission");
        try {
            result = bpmnDeploymentService.deployNewBpmnWithString(xml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (result.contains("Exception")) {
            logger.warn(result);
            ((BpmnAnalysis) analysis).setDeploymentSuccesful(false);
            try {
                JSONObject obj = new JSONObject(result);
                String message = obj.getString("message");
                String typ = obj.getString("type");
                ((BpmnAnalysis) analysis).setError(typ);
                ((BpmnAnalysis) analysis).setErrorDescription(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            ((BpmnAnalysis) analysis).setDeploymentSuccesful(true);
            try {
                JSONObject obj = new JSONObject(result);
                JSONObject responseObject = obj.getJSONObject("deployedProcessDefinitions");
                Iterator definitionID = obj.getJSONObject("deployedProcessDefinitions").keys();
                while (definitionID.hasNext()) {
                    String definitionId = definitionID.next().toString();
                    ((BpmnAnalysis) analysis).getCurrentDefinitionId().add(definitionId);
                    ((BpmnAnalysis) analysis).getCurrentIds().add(responseObject.getJSONObject(definitionId).getString("key"));
                }
                logger.info("---Deployed!---");
            } catch (Exception e) {
                throw new Exception("JSON Exception:" + e.toString());
            }
        }

    }

    private TestConfigDTO fetchTestConfig(int exerciseId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + serverProperties.getPort() + "/bpmn/exercise/solution/id/" + exerciseId))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) return objectMapper.readValue(response.body(), TestConfigDTO.class);
        throw new IOException("no config");
    }

    private List<TestConfigDTO> fetchBpmnConfig(int exerciseId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + serverProperties.getPort() + "/bpmn/exercise/solution/id/" + exerciseId))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        BpmnExcercise excercise = objectMapper.readValue(response.body(), BpmnExcercise.class);
        List<TestConfigDTO> list = new ArrayList<>(excercise.getTestConfigSet());
        if (response.statusCode() == 200) return list;
        throw new IOException("no config");
    }

    private void removeProcessInstance(String id) {
        try {
            this.bpmnDeploymentService.deleteProcess(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private boolean gradeByTestEngine(List<TestEngineDTO> engineDTOList, TestConfigDTO configDTO) {
//        if (engineDTOList == null || engineDTOList.size() == 0) return false;
//        for (TestEngineDTO e : engineDTOList) {
//            if (!configDTO.isShouldHaveCorrectOrder() && e.getTestEngineRuntimeDTO().isProcessInOrder()) {
//                logger.warn(configDTO.toString() + " ---- " + e);
//                logger.warn("first false");
//                return false;
//            } else if (configDTO.isShouldHaveCorrectOrder() && !e.getTestEngineRuntimeDTO().isProcessInOrder()) {
//                logger.warn(configDTO.toString() + " ---- " + e);
//                logger.warn("second false");
//                return false;
//            } else {
//                return true;
//            }
//        }
//        logger.warn("3 false");
//        return false;
//    }

    private boolean gradeTestEngine(TestEngineDTO response, TestConfigDTO config) {
        if (response == null || config == null) return false;
        if (config.isWrongPath() && response.getTestEngineRuntimeDTO().isProcessInOrder()) {
            return false;
        } else if (!config.isWrongPath() && !response.getTestEngineRuntimeDTO().isProcessInOrder()) {
            return false;
        } else {
            return true;
        }
    }
}
