package at.jku.dke.etutor.bpmn.dispatcher.rest.controller;

import at.jku.dke.etutor.bpmn.dispatcher.ETutorCORSPolicy;
import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities.BpmnExcercise;
import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.entities.TestConfigDTO;
import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.repositories.BpmnExcerciseRepository;
import at.jku.dke.etutor.bpmn.dispatcher.rest.dto.repositories.TestConfigDTORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = ETutorCORSPolicy.CORS_POLICY)
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    private final BpmnExcerciseRepository repo;
    private final TestConfigDTORepository testConfigDTORepository;

    public TestController(BpmnExcerciseRepository repo, TestConfigDTORepository testConfigDTORepository) {
        this.repo = repo;
        this.testConfigDTORepository = testConfigDTORepository;
    }

    @PostMapping("bpmn")
    public void save(@RequestBody Set<TestConfigDTO> excercise) {
        BpmnExcercise test = this.repo.save(new BpmnExcercise());
        excercise.forEach((x) -> x.setBpmnExcercise(test));
        List<TestConfigDTO> set = this.testConfigDTORepository.saveAll(excercise);
        logger.warn(set.toString());
    }

    @GetMapping("getTest")
    public void get(@RequestBody Set<TestConfigDTO> excercise) {
        BpmnExcercise test = this.repo.save(new BpmnExcercise());
        excercise.forEach((x) -> x.setBpmnExcercise(test));
        List<TestConfigDTO> set = this.testConfigDTORepository.saveAll(excercise);
        logger.warn(set.toString());
    }


    @PostMapping(path = "")
    public ResponseEntity<String> deployBPMN(@RequestBody String bpmnXml) {
        JSONObject jsonObject_failed = new JSONObject();
        try {
            jsonObject_failed.put("result", "Parse not available");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (bpmnXml == null || bpmnXml.isBlank()) return ResponseEntity.status(500).body(jsonObject_failed.toString());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("test", "test");
            jsonObject.put("xml", bpmnXml);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info(jsonObject.toString());
        return ResponseEntity.ok(jsonObject.toString());
    }
}
