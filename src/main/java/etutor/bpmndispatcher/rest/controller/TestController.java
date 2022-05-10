package etutor.bpmndispatcher.rest.controller;

import etutor.bpmndispatcher.rest.service.bpmnValidationModul.BpmnDeploymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    private final BpmnDeploymentService bpmnDeploymentService;

    public TestController(BpmnDeploymentService bpmnDeploymentService) {
        this.bpmnDeploymentService = bpmnDeploymentService;
    }


    @PostMapping(path = "")
    public ResponseEntity<String> deployBPMN(@RequestBody String bpmnXml) {
        if (bpmnXml == null || bpmnXml.isBlank()) return ResponseEntity.status(500).body("no Bpmn");
        logger.info(bpmnXml);

//        try {
//            String result = bpmnDeploymentService.deployNewBpmnWithString(bpmnXml);
//            if (result.contains("ParseException")) {
//                return result;
//            } else {
//                JSONObject obj = new JSONObject(result);
//                String id = obj.getString("id");
//                logger.info("ID:+++++++" + id);
//                String definitionID = obj.getJSONObject("deployedProcessDefinitions").keys().next().toString();
//                logger.warn(definitionID);
//                logger.info("---Deployed!---");
//                return definitionID;
//            }
//        } catch (IOException e) {
//            logger.warn(e.getMessage());
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
        return ResponseEntity.ok("Deployment");
    }
}
